package com.stellive.fansite.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stellive.fansite.domain.Channel;
import com.stellive.fansite.domain.Video;
import com.stellive.fansite.domain.VideoList;
import com.stellive.fansite.utils.ApiUtils;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.stellive.fansite.utils.YoutubeApiConst.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class YoutubeApiClient {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final ApiUtils apiUtils;

    public List<Video> getChannelVideos(String channelId, Integer maxResults) {

        URI uri = getChannelVideosUri(channelId, maxResults);

        try {
            ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);
            if (response.getStatusCode().is2xxSuccessful() && response.hasBody()) {
                String result = response.getBody();
                log.info("result={}", result);
                List<Video> videos = objectMapper.readValue(result, VideoList.class).getVideos();
                log.info("videos={}", videos);
                return videos;
            } else {
                log.error("YouTube API responded with status code: {}", response.getStatusCode());
                return Collections.emptyList();
            }
        } catch (RestClientException e) {
            throw new RestTemplateApiException("API call error", e);
        } catch (JsonProcessingException e) {
            throw new JsonParsingException("JSON parsing error", e);
        }
    }

    public Channel getChannel(String channelId) {
        URI uri = getChannelUri(channelId);

        try{
            ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);
            if (response.getStatusCode().is2xxSuccessful() && response.hasBody()) {
                String result = response.getBody();
//                log.info("getChannel result={}", result);
                Channel channel = objectMapper.readValue(result, Channel.class);
//                log.info("channel={}", channel);
                return channel;
            }else{
                log.error("YouTube API responded with status code: {}", response.getStatusCode());
                return new Channel();
            }
        } catch (RestClientException e) {
            throw new RestTemplateApiException("API call error", e);
        }  catch (JsonProcessingException e) {
            throw new JsonParsingException("JSON parsing error", e);
        }
    }

    private URI getChannelVideosUri(String channelId, Integer maxResults) {
        return UriComponentsBuilder.fromHttpUrl(URL_SEARCH)
                .queryParam(PARAM_SEARCH_PART, PART_SNIPPET)
                .queryParam(PARAM_SEARCH_CHANNEL_ID, channelId)
                .queryParam(PARAM_SEARCH_MAX_RESULTS, maxResults)
                .queryParam(PARAM_SEARCH_ORDER, ORDER_DATE)
                .queryParam(PARAM_SEARCH_TYPE, TYPE_VIDEO)
                .queryParam(PARAM_SEARCH_KEY, apiUtils.getYoutubeApiKey())
                .build().toUri();
    }

    private URI getChannelUri(String channelId) {
        return UriComponentsBuilder.fromHttpUrl(URL_CHANNEL)
                .queryParam(PARAM_CHANNEL_PART, PART_SNIPPET)
                .queryParam(PARAM_CHANNEL_ID, channelId)
                .queryParam(PARAM_CHANNEL_KEY, apiUtils.getYoutubeApiKey())
                .build().toUri();
    }


}


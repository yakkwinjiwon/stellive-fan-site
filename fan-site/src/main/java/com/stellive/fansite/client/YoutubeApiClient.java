package com.stellive.fansite.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stellive.fansite.client.searchlist.SearchItem;
import com.stellive.fansite.client.searchlist.SearchList;
import com.stellive.fansite.domain.Channel;
import com.stellive.fansite.domain.Video;
import com.stellive.fansite.domain.VideoList;
import com.stellive.fansite.client.channellist.ChannelList;
import com.stellive.fansite.client.channellist.ChannelItem;
import com.stellive.fansite.repository.YoutubeRepository;
import com.stellive.fansite.utils.ApiUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.stellive.fansite.utils.YoutubeApiConst.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class YoutubeApiClient {

    private final YoutubeRepository repository;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final ApiUtils apiUtils;

    public Channel getChannel(String channelId) {
        URI uri = getChannelUri(channelId);

        try {
            ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);
            if (response.getStatusCode().is2xxSuccessful() && response.hasBody()) {
                String result = response.getBody();
                ChannelList channelList = objectMapper.readValue(result, ChannelList.class);
                return buildChannel(channelList);
            } else {
                log.error("YouTube API responded with status code: {}", response.getStatusCode());
                return new Channel();
            }
        } catch (RestClientException e) {
            throw new RestTemplateApiException("API call error", e);
        } catch (JsonProcessingException e) {
            throw new JsonParsingException("JSON parsing error", e);
        } catch (Exception e) {
            throw new RuntimeException("error", e);
        }
    }


    public List<Video> getChannelVideos(String channelId, Integer maxResults) {

        URI uri = getChannelVideosUri(channelId, maxResults);

        try {
            ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);
            if (response.getStatusCode().is2xxSuccessful() && response.hasBody()) {
                String result = response.getBody();
                SearchList searchList = objectMapper.readValue(result, SearchList.class);
                List<SearchItem> items = searchList.getItems();
                List<Video> videos = new ArrayList<>();
                repository.findChannelById()

                items.forEach(item -> {
                    Video.builder()
                            .
                })
                return videos;
            } else {
                log.error("YouTube API responded with status code: {}", response.getStatusCode());
                return Collections.emptyList();
            }
        } catch (RestClientException e) {
            throw new RestTemplateApiException("API call error", e);
        } catch (JsonProcessingException e) {
            throw new JsonParsingException("JSON parsing error", e);
        } catch (Exception e) {
            throw new RuntimeException("error", e);
        }
    }

    private URI getChannelUri(String channelId) {
        return UriComponentsBuilder.fromHttpUrl(URL_CHANNEL)
                .queryParam(PARAM_CHANNEL_PART, PART_SNIPPET)
                .queryParam(PARAM_CHANNEL_ID, channelId)
                .queryParam(PARAM_CHANNEL_KEY, apiUtils.getYoutubeApiKey())
                .build().toUri();
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
    private Channel buildChannel(ChannelList channelList) {
        ChannelItem item = channelList.getItems().getFirst();
        return Channel.builder()
                .externalId(item.getId())
                .handle(item.getSnippet().getCustomUrl())
                .thumbnailUrl(item.getSnippet().getThumbnails().getHigh().getUrl())
                .build();
    }


}


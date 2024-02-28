package com.stellive.fansite.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stellive.fansite.domain.Video;
import com.stellive.fansite.domain.VideoList;
import com.stellive.fansite.utils.ApiUtils;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
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

    public ResponseEntity<String> getChannelVideos(String channelId, Integer maxResults) {

        URI uri = UriComponentsBuilder.fromHttpUrl(URL_SEARCH)
                .queryParam(PARAM_SEARCH_CHANNEL_ID, channelId)
                .queryParam(PARAM_SEARCH_MAX_RESULTS, maxResults)
                .queryParam(PARAM_SEARCH_ORDER, ORDER_DATE)
                .queryParam(PARAM_SEARCH_TYPE, TYPE_VIDEO)
                .queryParam(PARAM_SEARCH_PART, PART_SNIPPET)
                .queryParam(PARAM_SEARCH_KEY, apiUtils.getYoutubeApiKey())
                .build().toUri();

        String result = restTemplate.getForObject(uri, String.class);
        log.info("getChannelVideos={}", result);
        try {
            List<Video> videos = objectMapper.readValue(result, VideoList.class).getVideos();
            log.info("videos={}", videos);

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return null;
    }


}


package com.stellive.fansite.client;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stellive.fansite.domain.Video;
import com.stellive.fansite.domain.Videos;
import com.stellive.fansite.utils.ApiUtils;
import com.stellive.fansite.utils.YoutubeApiConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

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
            List<Video> videos = objectMapper.readValue(result, Videos.class).getVideos();
            log.info("videos={}", videos);

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}

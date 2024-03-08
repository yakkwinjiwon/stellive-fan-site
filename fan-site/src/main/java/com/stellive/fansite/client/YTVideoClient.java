package com.stellive.fansite.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stellive.fansite.dto.searchlist.SearchList;
import com.stellive.fansite.dto.searchlist.SearchSnippet;
import com.stellive.fansite.dto.searchlist.SearchItem;
import com.stellive.fansite.domain.YTUser;
import com.stellive.fansite.domain.Stella;
import com.stellive.fansite.domain.YTVideo;
import com.stellive.fansite.exceptions.JsonParsingException;
import com.stellive.fansite.repository.YTRepository;
import com.stellive.fansite.utils.ApiUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.stellive.fansite.utils.YTApiConst.*;
import static com.stellive.fansite.utils.YTApiConst.TYPE_VIDEO;

@Component
@RequiredArgsConstructor
@Slf4j
public class YTVideoClient {

    private final YTRepository repository;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final ApiUtils apiUtils;

    public List<YTVideo> getYTVideos(Stella stella, Integer maxResults) {
        ResponseEntity<String> response = fetchSearch(stella, maxResults);
        try {
            List<YTVideo> videos = parseSearch(stella, response);
            log.info("Fetched Youtube Videos={}", videos);
            return videos;
        } catch (JsonProcessingException e) {
            throw new JsonParsingException("JSON parsing error", e);
        }
    }

    private ResponseEntity<String> fetchSearch(Stella stella, Integer maxResults) {
        URI uri = getSearchUri(stella, maxResults);
        return restTemplate.getForEntity(uri, String.class);
    }

    private URI getSearchUri(Stella stella, Integer maxResults) {
        return UriComponentsBuilder.fromHttpUrl(URL_SEARCH)
                .queryParam(PARAM_SEARCH_KEY, apiUtils.getYoutubeApiKey())
                .queryParam(PARAM_SEARCH_PART, PART_SNIPPET)
                .queryParam(PARAM_SEARCH_CHANNEL_ID, stella.getYoutubeId())
                .queryParam(PARAM_SEARCH_MAX_RESULTS, maxResults)
                .queryParam(PARAM_SEARCH_ORDER, ORDER_DATE)
                .queryParam(PARAM_SEARCH_TYPE, TYPE_VIDEO)
                .build().toUri();
    }

    private List<YTVideo> parseSearch(Stella stella, ResponseEntity<String> response) throws JsonProcessingException {
        if (response.getStatusCode().is2xxSuccessful() && response.hasBody()) {
            String result = response.getBody();
            SearchList searchList = objectMapper.readValue(result, SearchList.class);
            return buildYTVideos(stella, searchList);
        } else {
            log.error("YouTube API responded with status code: {}", response.getStatusCode());
            return Collections.emptyList();
        }
    }

    private List<YTVideo> buildYTVideos(Stella stella, SearchList searchList) {
        List<YTVideo> videos = new ArrayList<>();
        List<SearchItem> items = searchList.getItems();
        items.forEach(item -> {
            SearchSnippet snippet = item.getSnippet();
            YTVideo video = YTVideo.builder()
                    .user(repository.findYTUserById(stella.getId()).orElseGet(YTUser::new))
                    .externalId(item.getId().getVideoId())
                    .publishTime(Instant.parse(snippet.getPublishTime()))
                    .title(snippet.getTitle())
                    .thumbnailUrl(snippet.getThumbnails().getHigh().getUrl())
                    .build();
            videos.add(video);
        });

        return videos;
    }
}

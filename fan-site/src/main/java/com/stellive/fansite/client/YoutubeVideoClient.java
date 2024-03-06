package com.stellive.fansite.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stellive.fansite.client.searchlist.SearchItem;
import com.stellive.fansite.client.searchlist.SearchList;
import com.stellive.fansite.client.searchlist.SearchSnippet;
import com.stellive.fansite.domain.Channel;
import com.stellive.fansite.domain.ChannelId;
import com.stellive.fansite.domain.Video;
import com.stellive.fansite.exceptions.JsonParsingException;
import com.stellive.fansite.repository.YoutubeRepository;
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

import static com.stellive.fansite.utils.YoutubeApiConst.*;
import static com.stellive.fansite.utils.YoutubeApiConst.TYPE_VIDEO;

@Component
@RequiredArgsConstructor
@Slf4j
public class YoutubeVideoClient {

    private final YoutubeRepository repository;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final ApiUtils apiUtils;

    public List<Video> getVideos(ChannelId channelId, Integer maxResults) {
        ResponseEntity<String> response = fetchSearch(channelId.getYoutubeId(), maxResults);
        try {
            List<Video> videos = parseVideos(response, channelId.getId());
            log.info("Fetched Youtube Videos={}", videos);
            return videos;
        } catch (JsonProcessingException e) {
            throw new JsonParsingException("JSON parsing error", e);
        }
    }

    private ResponseEntity<String> fetchSearch(String externalId, Integer maxResults) {
        URI uri = getSearchUri(externalId, maxResults);
        return restTemplate.getForEntity(uri, String.class);
    }

    private URI getSearchUri(String externalId, Integer maxResults) {
        return UriComponentsBuilder.fromHttpUrl(URL_SEARCH)
                .queryParam(PARAM_SEARCH_KEY, apiUtils.getYoutubeApiKey())
                .queryParam(PARAM_SEARCH_PART, PART_SNIPPET)
                .queryParam(PARAM_SEARCH_CHANNEL_ID, externalId)
                .queryParam(PARAM_SEARCH_MAX_RESULTS, maxResults)
                .queryParam(PARAM_SEARCH_ORDER, ORDER_DATE)
                .queryParam(PARAM_SEARCH_TYPE, TYPE_VIDEO)
                .build().toUri();
    }

    private List<Video> parseVideos(ResponseEntity<String> response, Long id) throws JsonProcessingException {
        if (response.getStatusCode().is2xxSuccessful() && response.hasBody()) {
            String result = response.getBody();
            SearchList searchList = objectMapper.readValue(result, SearchList.class);

            Channel channel = repository.findChannelById(id).orElseGet(Channel::new);
            return buildVideos(searchList, channel);
        } else {
            log.error("YouTube API responded with status code: {}", response.getStatusCode());
            return Collections.emptyList();
        }
    }

    private static List<Video> buildVideos(SearchList searchList, Channel channel) {
        List<Video> videos = new ArrayList<>();
        List<SearchItem> items = searchList.getItems();
        items.forEach(item -> {
            SearchSnippet snippet = item.getSnippet();
            Video video = Video.builder()
                    .channel(channel)
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

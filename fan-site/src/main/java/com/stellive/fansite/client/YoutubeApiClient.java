package com.stellive.fansite.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stellive.fansite.client.searchlist.SearchItem;
import com.stellive.fansite.client.searchlist.SearchList;
import com.stellive.fansite.client.searchlist.SearchSnippet;
import com.stellive.fansite.domain.Channel;
import com.stellive.fansite.client.channellist.ChannelList;
import com.stellive.fansite.client.channellist.ChannelItem;
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
import java.util.Optional;

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
        ResponseEntity<String> response = fetchChannel(channelId);
        try {
            return parseChannel(response);
        } catch (JsonProcessingException e) {
            throw new JsonParsingException("JSON parsing error", e);
        }
    }
    public List<Video> getVideos(String channelId, Integer maxResults) {
        ResponseEntity<String> response = fetchSearch(channelId, maxResults);
        try {
            return parseVideos(channelId, response);
        } catch (JsonProcessingException e) {
            throw new JsonParsingException("JSON parsing error", e);
        }
    }

    private ResponseEntity<String> fetchChannel(String channelId) {
        URI uri = getChannelUri(channelId);
        return restTemplate.getForEntity(uri, String.class);
    }
    private ResponseEntity<String> fetchSearch(String channelId, Integer maxResults) {
        URI uri = getSearchUri(channelId, maxResults);
        return restTemplate.getForEntity(uri, String.class);
    }

    private URI getChannelUri(String channelId) {
        return UriComponentsBuilder.fromHttpUrl(URL_CHANNEL)
                .queryParam(PARAM_CHANNEL_KEY, apiUtils.getYoutubeApiKey())
                .queryParam(PARAM_CHANNEL_PART, PART_SNIPPET)
                .queryParam(PARAM_CHANNEL_ID, channelId)
                .build().toUri();
    }
    private URI getSearchUri(String channelId, Integer maxResults) {
        return UriComponentsBuilder.fromHttpUrl(URL_SEARCH)
                .queryParam(PARAM_SEARCH_KEY, apiUtils.getYoutubeApiKey())
                .queryParam(PARAM_SEARCH_PART, PART_SNIPPET)
                .queryParam(PARAM_SEARCH_CHANNEL_ID, channelId)
                .queryParam(PARAM_SEARCH_MAX_RESULTS, maxResults)
                .queryParam(PARAM_SEARCH_ORDER, ORDER_DATE)
                .queryParam(PARAM_SEARCH_TYPE, TYPE_VIDEO)
                .build().toUri();
    }

    private Channel parseChannel(ResponseEntity<String> response) throws JsonProcessingException {
        if (response.getStatusCode().is2xxSuccessful() && response.hasBody()) {
            String result = response.getBody();
            ChannelList channelList = objectMapper.readValue(result, ChannelList.class);
            return buildChannel(channelList);
        } else {
            log.error("YouTube API responded with status code: {}", response.getStatusCode());
            return new Channel();
        }
    }
    private List<Video> parseVideos(String channelId, ResponseEntity<String> response) throws JsonProcessingException {
        if (response.getStatusCode().is2xxSuccessful() && response.hasBody()) {
            String result = response.getBody();
            SearchList searchList = objectMapper.readValue(result, SearchList.class);
            List<SearchItem> items = searchList.getItems();
            Channel channel = repository.findChannelByExternalId(channelId).orElseGet(Channel::new);
            return buildVideos(items, channel);
        } else {
            log.error("YouTube API responded with status code: {}", response.getStatusCode());
            return Collections.emptyList();
        }
    }

    private Channel buildChannel(ChannelList channelList) {
        ChannelItem item = channelList.getItems().getFirst();
        return Channel.builder()
                .externalId(item.getId())
                .handle(item.getSnippet().getCustomUrl())
                .thumbnailUrl(item.getSnippet().getThumbnails().getHigh().getUrl())
                .build();
    }
    private static List<Video> buildVideos(List<SearchItem> items, Channel channel) {
        List<Video> videos = new ArrayList<>();
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


package com.stellive.fansite.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stellive.fansite.domain.VideoType;
import com.stellive.fansite.dto.search.SearchList;
import com.stellive.fansite.dto.search.SearchSnippet;
import com.stellive.fansite.dto.search.SearchItem;
import com.stellive.fansite.domain.Channel;
import com.stellive.fansite.domain.YoutubeChannel;
import com.stellive.fansite.domain.Video;
import com.stellive.fansite.exceptions.JsonParsingException;
import com.stellive.fansite.repository.Channel.ChannelRepo;
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
public class VideoClient {

    private final ChannelRepo channelRepo;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final ApiUtils apiUtils;

    public List<Video> getVideos(YoutubeChannel stella, VideoType videoType,
                                 Integer maxResults) {

        ResponseEntity<String> response = fetchSearch(stella, videoType, maxResults);
        try {
            List<Video> videos = parseSearch(stella, videoType, response);
            log.info("Fetched Youtube Videos={}", videos);
            return videos;
        } catch (JsonProcessingException e) {
            throw new JsonParsingException("JSON parsing error", e);
        }
    }

    private ResponseEntity<String> fetchSearch(YoutubeChannel stella, VideoType videoType,
                                               Integer maxResults) {
        URI uri = getSearchUri(stella, videoType, maxResults);
        return restTemplate.getForEntity(uri, String.class);
    }

    private URI getSearchUri(YoutubeChannel stella, VideoType videoType,
                             Integer maxResults) {
        return UriComponentsBuilder.fromHttpUrl(URL_SEARCH)
                .queryParam(PARAM_KEY, apiUtils.getYoutubeApiKey())
                .queryParam(PARAM_SEARCH_PART, PART_SNIPPET)
                .queryParam(PARAM_SEARCH_CHANNEL_ID, stella.getChannelId())
                .queryParam(PARAM_SEARCH_MAX_RESULTS, maxResults)
                .queryParam(PARAM_SEARCH_ORDER, ORDER_DATE)
                .queryParam(PARAM_SEARCH_TYPE, TYPE_VIDEO)
                .build().toUri();
    }

    private List<Video> parseSearch(YoutubeChannel stella, VideoType videoType,
                                    ResponseEntity<String> response)
            throws JsonProcessingException {
        if (response.getStatusCode().is2xxSuccessful() && response.hasBody()) {
            String result = response.getBody();
            SearchList searchList = objectMapper.readValue(result, SearchList.class);
            return buildVideos(stella, videoType, searchList);
        } else {
            log.error("YouTube API responded with status code: {}", response.getStatusCode());
            return Collections.emptyList();
        }
    }

    private List<Video> buildVideos(YoutubeChannel stella, VideoType videoType,
                                    SearchList searchList) {
        List<Video> videos = new ArrayList<>();
        List<SearchItem> items = searchList.getItems();

        items.forEach(item -> {
            SearchSnippet snippet = item.getSnippet();
            Video video = Video.builder()
                    .videoType(videoType)
                    .channel(channelRepo.findById(stella.getId()).orElseGet(Channel::new))
                    .externalId(item.getId().getVideoId())
                    .title(snippet.getTitle())
                    .thumbnailUrl(snippet.getThumbnails().getHigh().getUrl())
                    .publishTime(Instant.parse(snippet.getPublishTime()))
                    .build();
            videos.add(video);
        });

        return videos;
    }
}

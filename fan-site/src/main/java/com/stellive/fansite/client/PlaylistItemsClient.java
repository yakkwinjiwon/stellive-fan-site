package com.stellive.fansite.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stellive.fansite.domain.Channel;
import com.stellive.fansite.domain.Video;
import com.stellive.fansite.dto.playlistitems.PlaylistItemsItem;
import com.stellive.fansite.dto.playlistitems.PlaylistItemsList;
import com.stellive.fansite.dto.playlistitems.PlaylistItemsSnippet;
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
import java.util.List;

import static com.stellive.fansite.utils.YoutubeApiConst.*;

@Component
@Slf4j
@RequiredArgsConstructor
public class PlaylistItemsClient {

    private final ApiUtils apiUtils;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    private final ChannelRepo channelRepo;

    public List<Video> getVieosFromPlaylist(String playlistId,
                                            boolean isInitialize) {
        List<Video> videos = new ArrayList<>();
        ResponseEntity<String> response = null;
        String nextPageToken = null;
        do {
            response = fetchPlaylistItems(playlistId, nextPageToken);
            PlaylistItemsList list = parseResponse(response);

            List<Video> parsedVideos = getVideosFromList(list);
            videos.addAll(parsedVideos);
            log.info("videos={}", parsedVideos);

            nextPageToken = list.getNextPageToken();

        } while (response.getBody().contains("nextPageToken") && isInitialize);

        return videos;
    }

    private PlaylistItemsList parseResponse(ResponseEntity<String> response) {
        try {
            return objectMapper.readValue(response.getBody(), PlaylistItemsList.class);
        } catch (JsonProcessingException e) {
            throw new JsonParsingException("JSON parsing error", e);
        }
    }

    public ResponseEntity<String> fetchPlaylistItems(String playlistId,
                                                     String nextPageToken) {
        URI uri = getUri(playlistId, nextPageToken);
        return restTemplate.getForEntity(uri, String.class);
    }

    private URI getUri(String playlistId, String nextPageToken) {

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(URL_PLAYLIST_ITEMS)
                .queryParam(PARAM_KEY, apiUtils.getYoutubeApiKey())
                .queryParam(PARAM_PLAYLIST_ITEMS_PART, PART_SNIPPET)
                .queryParam(PARAM_PLAYLIST_ITEMS_PLAYLIST_ID, playlistId)
                .queryParam(PARAM_PLAYLIST_ITEMS_MAX_RESULTS, MAX_RESULTS);
        if(nextPageToken != null) {
            builder = builder.queryParam(PARAM_PLAYLIST_ITEMS_PAGE_TOKEN, nextPageToken);
        }
        return builder.build().toUri();
    }

    private List<Video> getVideosFromList(PlaylistItemsList list) {

        List<PlaylistItemsItem> items = list.getItems();
        List<Video> videos = new ArrayList<>();
        items.forEach(item -> {
            PlaylistItemsSnippet snippet = item.getSnippet();
            //사용할 수 없는 동영상 제외
            if (snippet.getThumbnails().getHigh() != null) {
                Video video = buildVideo(snippet);
                videos.add(video);
            }
        });
        return videos;
    }

    private Video buildVideo(PlaylistItemsSnippet snippet) {

        return Video.builder()
                .channel(channelRepo.findByExternalId(snippet.getChannelId())
                        .orElseGet(Channel::new))
                .externalId(snippet.getResourceId().getVideoId())
                .title(snippet.getTitle())
                .thumbnailUrl(snippet.getThumbnails().getHigh().getUrl())
                .publishTime(Instant.parse(snippet.getPublishedAt()))
                .build();
    }

}

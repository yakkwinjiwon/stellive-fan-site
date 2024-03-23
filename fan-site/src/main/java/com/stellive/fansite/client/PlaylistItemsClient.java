package com.stellive.fansite.client;

import com.stellive.fansite.domain.Channel;
import com.stellive.fansite.domain.Video;
import com.stellive.fansite.dto.VideoResponse;
import com.stellive.fansite.dto.playlistitem.*;
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
import java.util.Optional;

import static com.stellive.fansite.utils.YoutubeApiConst.*;

@Component
@Slf4j
@RequiredArgsConstructor
public class PlaylistItemsClient {

    private final ChannelRepo channelRepo;

    private final RestTemplate restTemplate;
    private final ApiUtils apiUtils;

    public List<Video> getVideosFromPlaylistId(String playlistId,
                                               Integer maxResults) {
        List<Video> videos = new ArrayList<>();
        VideoResponse response = null;
        String nextPageToken = null;

        do {
            response = getVideosFromNextPageToken(playlistId, maxResults, nextPageToken);
            videos.addAll(response.getVideos());
            nextPageToken = response.getNextPageToken();
        } while (nextPageToken != null && maxResults.equals(MAX_RESULTS_ALL));

        return videos;
    }

    private VideoResponse getVideosFromNextPageToken(String playlistId,
                                                     Integer maxResults,
                                                     String nextPageToken) {
        ResponseEntity<String> response = fetchPlaylistItem(playlistId, maxResults, nextPageToken);
        PlaylistItemList list = apiUtils.parseResponse(response, PlaylistItemList.class);
        return VideoResponse.builder()
                .videos(buildVideos(list))
                .nextPageToken(Optional.ofNullable(list)
                        .map(PlaylistItemList::getNextPageToken)
                        .orElse(null))
                .build();
    }

    private ResponseEntity<String> fetchPlaylistItem(String playlistId,
                                                     Integer maxResults, String nextPageToken) {
        URI uri = getUri(playlistId, nextPageToken, maxResults);
        return restTemplate.getForEntity(uri, String.class);
    }

    private URI getUri(String playlistId,
                       String nextPageToken,
                       Integer maxResults) {
        UriComponentsBuilder builder = getUriBuilder(playlistId, maxResults);
        if (nextPageToken != null) {
            builder = builder.queryParam(PARAM_PLAYLIST_ITEMS_PAGE_TOKEN, nextPageToken);
        }
        return builder.build().toUri();
    }

    private UriComponentsBuilder getUriBuilder(String playlistId,
                                               Integer maxResults) {
        return UriComponentsBuilder.fromHttpUrl(URL_PLAYLIST_ITEMS)
                .queryParam(PARAM_KEY, apiUtils.getYoutubeApiKey())
                .queryParam(PARAM_PLAYLIST_ITEMS_PART, PART_SNIPPET)
                .queryParam(PARAM_PLAYLIST_ITEMS_PLAYLIST_ID, playlistId)
                .queryParam(PARAM_PLAYLIST_ITEMS_MAX_RESULTS, maxResults);
    }

    private List<Video> buildVideos(PlaylistItemList list) {
        List<Video> videos = new ArrayList<>();
        List<PlaylistItemItem> items = Optional.ofNullable(list)
                .map(PlaylistItemList::getItems)
                .orElse(new ArrayList<>());

        items.forEach(item -> {
            PlaylistItemSnippet snippet = Optional.ofNullable(item)
                    .map(PlaylistItemItem::getSnippet)
                    .orElse(null);
            //사용할 수 없는 동영상 제외
            if (Optional.ofNullable(snippet)
                    .map(PlaylistItemSnippet::getThumbnails)
                    .map(PlaylistItemThumbnails::getHigh).isPresent()) {
                videos.add(buildVideo(snippet));
            }
        });
        return videos;
    }

    private Video buildVideo(PlaylistItemSnippet snippet) {
        return Video.builder()
                .channel(channelRepo.findByExternalId(snippet.getVideoOwnerChannelId())
                        .orElseGet(Channel::new))
                .externalId(Optional.of(snippet)
                        .map(PlaylistItemSnippet::getResourceId)
                        .map(PlaylistItemResourceId::getVideoId)
                        .orElse(null))
                .title(Optional.of(snippet)
                        .map(PlaylistItemSnippet::getTitle)
                        .orElse(null))
                .thumbnailUrl(Optional.of(snippet)
                        .map(PlaylistItemSnippet::getThumbnails)
                        .map(PlaylistItemThumbnails::getHigh)
                        .map(PlaylistItemThumbnail::getUrl)
                        .orElse(null))
                .publishTime(Instant.parse(Optional.of(snippet)
                        .map(PlaylistItemSnippet::getPublishedAt)
                        .orElse(null)))
                .build();
    }

}

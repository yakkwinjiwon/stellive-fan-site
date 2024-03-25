package com.stellive.fansite.client;

import com.stellive.fansite.domain.Channel;
import com.stellive.fansite.domain.Video;
import com.stellive.fansite.dto.VideoResponse;
import com.stellive.fansite.dto.playlistitem.*;
import com.stellive.fansite.exceptions.ApiResponseException;
import com.stellive.fansite.exceptions.ResponseParsingException;
import com.stellive.fansite.repository.Channel.ChannelRepo;
import com.stellive.fansite.utils.ApiUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.stellive.fansite.utils.YoutubeApiConst.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class PlaylistItemRetryClient {

    private final RestTemplate restTemplate;
    private final ApiUtils apiUtils;

    private final ChannelRepo channelRepo;

    @Retryable(value = {RestClientException.class, ResponseParsingException.class,
            ApiResponseException.class},
            maxAttempts = 1, backoff = @Backoff(delay = 1000))
    public VideoResponse getVideosFromNextPageToken(String playlistId,
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
                                                     Integer maxResults,
                                                     String nextPageToken) {
        URI uri = getPlaylistItemUri(playlistId, maxResults, nextPageToken);
        return restTemplate.getForEntity(uri, String.class);
    }

    private URI getPlaylistItemUri(String playlistId,
                                   Integer maxResults,
                                   String nextPageToken) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(URL_PLAYLIST_ITEMS)
                .queryParam(PARAM_KEY, apiUtils.getYoutubeApiKey())
                .queryParam(PARAM_PART, PART_SNIPPET)
                .queryParam(PARAM_PLAYLIST_ID, playlistId)
                .queryParam(PARAM_MAX_RESULTS, maxResults);
        if (nextPageToken != null) {
            builder = builder.queryParam(PARAM_PAGE_TOKEN, nextPageToken);
        }
        return builder.build().toUri();
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

            if (Optional.ofNullable(snippet)
                    .map(PlaylistItemSnippet::getThumbnails)
                    .map(PlaylistItemThumbnails::getHigh)
                    .isPresent()) {
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
                        .orElse(""))
                .title(Optional.of(snippet)
                        .map(PlaylistItemSnippet::getTitle)
                        .orElse(""))
                .thumbnailUrl(Optional.of(snippet)
                        .map(PlaylistItemSnippet::getThumbnails)
                        .map(PlaylistItemThumbnails::getHigh)
                        .map(PlaylistItemThumbnail::getUrl)
                        .orElse(""))
                .publishTime(Instant.parse(Optional.of(snippet)
                        .map(PlaylistItemSnippet::getPublishedAt)
                        .orElse("1970-01-01T00:00:00Z")))
                .build();
    }

}

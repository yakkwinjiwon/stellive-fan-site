package com.stellive.fansite.api;

import com.stellive.fansite.domain.Video;
import com.stellive.fansite.dto.etc.VideoResult;
import com.stellive.fansite.dto.playlistitem.*;
import com.stellive.fansite.repository.Channel.ChannelRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.stellive.fansite.utils.YoutubeApiConst.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class PlaylistItemFetcher {

    private final PlaylistItemConnector playlistItemConnector;

    private final ChannelRepo channelRepo;

    public List<Video> fetchVideos(String playlistId,
                                   Integer maxResults) {
        List<Video> videos = new ArrayList<>();
        VideoResult response;
        String nextPageToken = null;

        do {
            response = fetchVideos(playlistId, maxResults, nextPageToken);
            videos.addAll(response.getVideos());
            nextPageToken = response.getNextPageToken();
        } while (shouldContinueFetching(maxResults, nextPageToken));

        return videos;
    }

    private boolean shouldContinueFetching(Integer maxResults, String nextPageToken) {
        return nextPageToken != null && maxResults.equals(MAX_RESULTS_ALL);
    }

    private VideoResult fetchVideos(String playlistId,
                                    Integer maxResults,
                                    String nextPageToken) {
        PlaylistItemList list = playlistItemConnector.callPlaylistItem(playlistId, maxResults, nextPageToken);
        return buildVideoResponse(list);
    }

    private VideoResult buildVideoResponse(PlaylistItemList list) {
        return VideoResult.builder()
                .videos(buildVideos(list))
                .nextPageToken(getNextPageToken(list))
                .build();
    }

    private String getNextPageToken(PlaylistItemList list) {
        return Optional.ofNullable(list)
                .map(PlaylistItemList::getNextPageToken)
                .orElse(null);
    }

    private List<Video> buildVideos(PlaylistItemList list) {
        List<Video> videos = new ArrayList<>();
        List<PlaylistItemItem> items = getItems(list);

        items.forEach(item -> {
            PlaylistItemSnippet snippet = getSnippet(item);

            // 볼 수 없는 영상은 썸네일이 없음
            if (isThumbnailPresent(snippet)) {
                Video video = buildVideo(snippet);
                videos.add(video);
            }
        });
        return videos;
    }

    private boolean isThumbnailPresent(PlaylistItemSnippet snippet) {
        return Optional.ofNullable(snippet)
                .map(PlaylistItemSnippet::getThumbnails)
                .map(PlaylistItemThumbnails::getHigh)
                .isPresent();
    }

    private PlaylistItemSnippet getSnippet(PlaylistItemItem item) {
        return Optional.ofNullable(item)
                .map(PlaylistItemItem::getSnippet)
                .orElse(null);
    }

    private List<PlaylistItemItem> getItems(PlaylistItemList list) {
        return Optional.ofNullable(list)
                .map(PlaylistItemList::getItems)
                .orElse(new ArrayList<>());
    }

    private Video buildVideo(PlaylistItemSnippet snippet) {
        return Video.builder()
                .channel(channelRepo.findByExternalId(snippet.getVideoOwnerChannelId())
                        .orElse(null))
                .externalId(getExternalId(snippet))
                .title(getTitle(snippet))
                .thumbnailUrl(getThumbnailUrl(snippet))
                .publishTime(getPublishTime(snippet))
                .build();
    }

    private Instant getPublishTime(PlaylistItemSnippet snippet) {
        return Instant.parse(Optional.of(snippet)
                .map(PlaylistItemSnippet::getPublishedAt)
                .orElse("1970-01-01T00:00:00Z"));
    }

    private String getThumbnailUrl(PlaylistItemSnippet snippet) {
        return Optional.of(snippet)
                .map(PlaylistItemSnippet::getThumbnails)
                .map(PlaylistItemThumbnails::getHigh)
                .map(PlaylistItemThumbnail::getUrl)
                .orElse("");
    }

    private String getTitle(PlaylistItemSnippet snippet) {
        return Optional.of(snippet)
                .map(PlaylistItemSnippet::getTitle)
                .orElse("");
    }

    private String getExternalId(PlaylistItemSnippet snippet) {
        return Optional.of(snippet)
                .map(PlaylistItemSnippet::getResourceId)
                .map(PlaylistItemResourceId::getVideoId)
                .orElse("");
    }

}

package com.stellive.fansite.api;

import com.stellive.fansite.domain.Video;
import com.stellive.fansite.dto.etc.VideoResult;
import com.stellive.fansite.dto.playlistitem.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.stellive.fansite.utils.YoutubeApiConst.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class PlaylistItemFetcher {

    private final PlaylistItemConnector playlistItemConnector;

    public List<String> fetchPlaylistItem(String playlistId,
                                   Integer maxResults) {
        List<String> videoIds = new ArrayList<>();
        VideoResult response;
        String nextPageToken = null;

        do {
            response = fetchPlaylistItem(playlistId, maxResults, nextPageToken);
            videoIds.addAll(response.getVideoIds());
            nextPageToken = response.getNextPageToken();
        } while (shouldContinueFetching(maxResults, nextPageToken));

        return videoIds;
    }

    private boolean shouldContinueFetching(Integer maxResults, String nextPageToken) {
        return nextPageToken != null && maxResults.equals(MAX_RESULTS_ALL);
    }

    private VideoResult fetchPlaylistItem(String playlistId,
                                          Integer maxResults,
                                          String nextPageToken) {
        PlaylistItemList list = playlistItemConnector.callPlaylistItem(playlistId, maxResults, nextPageToken);
        return buildVideoResponse(list);
    }

    private VideoResult buildVideoResponse(PlaylistItemList list) {
        return VideoResult.builder()
                .videoIds(getVideoIds(list))
                .nextPageToken(getNextPageToken(list))
                .build();
    }

    private String getNextPageToken(PlaylistItemList list) {
        return Optional.ofNullable(list)
                .map(PlaylistItemList::getNextPageToken)
                .orElse(null);
    }

    private List<String> getVideoIds(PlaylistItemList list) {
        List<String> videoIds = new ArrayList<>();
        List<PlaylistItemItem> items = getItems(list);

        items.forEach(item -> {
            // 볼 수 없는 영상은 썸네일이 없음
            if (isThumbnailPresent(item)) {
                videoIds.add(getVideoId(item));
            }
        });
        return videoIds;
    }

    private String getVideoId(PlaylistItemItem item) {
        return Optional.ofNullable(item)
                .map(PlaylistItemItem::getSnippet)
                .map(PlaylistItemSnippet::getResourceId)
                .map(PlaylistItemResourceId::getVideoId)
                .orElse("");
    }

    private PlaylistItemSnippet getSnippet(PlaylistItemItem item) {
        return Optional.ofNullable(item)
                .map(PlaylistItemItem::getSnippet)
                .orElse(null);
    }

    private boolean isThumbnailPresent(PlaylistItemItem item) {
        return Optional.ofNullable(item)
                .map(PlaylistItemItem::getSnippet)
                .map(PlaylistItemSnippet::getThumbnails)
                .map(PlaylistItemThumbnails::getHigh)
                .isPresent();
    }

    private List<PlaylistItemItem> getItems(PlaylistItemList list) {
        return Optional.ofNullable(list)
                .map(PlaylistItemList::getItems)
                .orElse(new ArrayList<>());
    }


}

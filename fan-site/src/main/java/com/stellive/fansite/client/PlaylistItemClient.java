package com.stellive.fansite.client;

import com.stellive.fansite.domain.Video;
import com.stellive.fansite.dto.VideoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.stellive.fansite.utils.YoutubeApiConst.MAX_RESULTS_ALL;

@Component
@RequiredArgsConstructor
public class PlaylistItemClient {

    private final PlaylistItemRetryClient retryClient;

    public List<Video> getVideosFromPlaylistId(String playlistId,
                                               Integer maxResults) {
        List<Video> videos = new ArrayList<>();
        VideoResponse response = null;
        String nextPageToken = null;

        do {
            response = retryClient.getVideosFromNextPageToken(playlistId, maxResults, nextPageToken);
            videos.addAll(response.getVideos());
            nextPageToken = response.getNextPageToken();
        } while (nextPageToken != null && maxResults.equals(MAX_RESULTS_ALL));

        return videos;
    }
}

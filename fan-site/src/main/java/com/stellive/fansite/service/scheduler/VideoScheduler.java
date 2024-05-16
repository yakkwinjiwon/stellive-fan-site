package com.stellive.fansite.service.scheduler;

import com.stellive.fansite.api.Youtube.PlaylistItemFetcher;
import com.stellive.fansite.api.Youtube.VideoFetcher;
import com.stellive.fansite.domain.Video;
import com.stellive.fansite.domain.VideoType;
import com.stellive.fansite.domain.YoutubeChannel;
import com.stellive.fansite.repository.Video.VideoRepo;
import com.stellive.fansite.utils.ApiUtils;
import com.stellive.fansite.utils.AppUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.stellive.fansite.utils.AppConst.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class VideoScheduler {

    private final PlaylistItemFetcher playlistItemFetcher;
    private final VideoFetcher videoFetcher;

    private final VideoRepo videoRepo;

    public List<Video> updateVideos(Integer maxResults) {
        log.info("Update Videos");
        List<Video> videos = new ArrayList<>();

        ApiUtils.executeForEachChannel(youtubeChannel -> {
            String playlistId = getAllVideoPlaylistId(youtubeChannel);
            List<String> videoIds = playlistItemFetcher.fetchVideoIds(playlistId, maxResults);
            removeExistingVideoIds(videoIds);

            VideoType videoType = youtubeChannel.isReplay() ? VideoType.REPLAY : VideoType.UNKNOWN;
            List<Video> fetchedVideos = videoFetcher.fetchVideos(videoIds, videoType);
            List<Video> updatedVideos = updateVideos(fetchedVideos);

            log.info("Updated Videos={}, size={}", updatedVideos.getFirst(), updatedVideos.size());
            videos.addAll(updatedVideos);
        });
        return videos;
    }

    private List<Video> updateVideos(List<Video> videos) {
        return videoRepo.save(videos);
    }

    // 유튜브 id를 이용하여 전체 영상 플레이리스트 id를 생성.
    // UCxxx (id) -> UUxxx (playlistId)
    private String getAllVideoPlaylistId(YoutubeChannel youtubeChannel) {
        StringBuilder builder = new StringBuilder(youtubeChannel.getChannelId());
        builder.setCharAt(1, 'U');
        return builder.toString();
    }

    // API 사용량을 줄이기 위해 이미 존재하는 영상 id를 제거
    private void removeExistingVideoIds(List<String> videoIds) {
        List<String> externalIds = AppUtils.extractFields(videoRepo.findAll(), FIELD_EXTERNAL_ID, String.class);
        videoIds.removeAll(externalIds);
    }
}

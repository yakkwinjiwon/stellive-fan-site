package com.stellive.fansite.service.scheduling;

import com.stellive.fansite.api.PlaylistItemFetcher;
import com.stellive.fansite.api.VideoFetcher;
import com.stellive.fansite.domain.Video;
import com.stellive.fansite.domain.VideoType;
import com.stellive.fansite.domain.YoutubeChannel;
import com.stellive.fansite.repository.Video.VideoRepo;
import com.stellive.fansite.utils.ApiUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class VideoScheduler {

    private final ApiUtils apiUtils;

    private final PlaylistItemFetcher playlistItemFetcher;
    private final VideoFetcher videoFecther;

    private final VideoRepo videoRepo;

    public List<Video> updateVideos(Integer maxResults) {
        log.info("Update Videos");
        List<Video> videos = new ArrayList<>();

        apiUtils.executeForEachChannel(youtubeChannel -> {
            List<Video> fetchedVideos = playlistItemFetcher.fetchVideos(getAllVideoPlaylistId(youtubeChannel), maxResults);
            fetchedVideos.forEach(videoFecther::setVideoDuration);
            List<Video> determinedVideos = determineVideoType(youtubeChannel, fetchedVideos);
            List<Video> updatedVideos = updateVideos(determinedVideos);

            log.info("Updated Videos={}", updatedVideos);
            videos.addAll(updatedVideos);
        });
        return videos;
    }

    private List<Video> updateVideos(List<Video> videos) {
        return videoRepo.save(videos);
    }

    private List<Video> determineVideoType(YoutubeChannel youtubeChannel,
                                           List<Video> fetchedVideos) {
        if (youtubeChannel.isReplay()) {
            fetchedVideos.forEach(video -> video.setVideoType(VideoType.REPLAY));
            return fetchedVideos;
        }

        fetchedVideos.forEach(video -> {
            if (video.getDuration() <= 60) {
                video.setVideoType(VideoType.SHORTS);
            } else {
                video.setVideoType(VideoType.VIDEO);
            }
        });
        return fetchedVideos;
    }

    // 유튜브 id를 이용하여 전체 영상 플레이리스트 id를 생성.
    // UCxxx (id) -> UUxxx (playlistId)
    private String getAllVideoPlaylistId(YoutubeChannel youtubeChannel) {
        StringBuilder builder = new StringBuilder(youtubeChannel.getChannelId());
        builder.setCharAt(1, 'U');
        return builder.toString();
    }

    public List<Video> updateMusics(Integer maxResults) {
        log.info("Update Musics");
        List<Video> videos = new ArrayList<>();

        apiUtils.executeForEachChannel(youtubeChannel -> {
            updateMusics(youtubeChannel, maxResults);
        });
        return videos;
    }

    private List<Video> updateMusics(YoutubeChannel youtubeChannel,
                                     Integer maxResults) {
        List<Video> videos = new ArrayList<>();

        youtubeChannel.getMusicPlaylistIds().forEach(playlistId -> {
            apiUtils.executeWithHandling(() -> {
                List<Video> fetchedVideos = playlistItemFetcher.fetchVideos(playlistId, maxResults);

                fetchedVideos.forEach(video -> {
                    video.setVideoType(VideoType.MUSIC);
                    videoFecther.setVideoDuration(video);
                });

                List<Video> updatedVideos = updateVideos(fetchedVideos);
                log.info("Updated Musics={}", updatedVideos);

                videos.addAll(updatedVideos);
                return null;
            });
        });

        return videos;
    }
}

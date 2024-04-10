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
    private final VideoFetcher videoFetcher;

    private final VideoRepo videoRepo;

    public List<Video> updateVideos(Integer maxResults) {
        log.info("Update Videos");
        List<Video> videos = new ArrayList<>();

        apiUtils.executeForEachChannel(youtubeChannel -> {
            List<String> videoIds = playlistItemFetcher.fetchPlaylistItem(getAllVideoPlaylistId(youtubeChannel), maxResults);
            VideoType videoType = youtubeChannel.isReplay() ? VideoType.REPLAY : VideoType.VIDEO;
            List<Video> fetchedVideos = videoFetcher.fetchVideos(videoIds, videoType);
            List<Video> updatedVideos = updateVideos(fetchedVideos);

            log.info("Updated Videos={}", updatedVideos);
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

    public List<Video> updateMusics(Integer maxResults) {
        log.info("Update Musics");
        List<Video> musics = new ArrayList<>();

        apiUtils.executeForEachChannel(youtubeChannel -> {
            List<Video> updatedMusics = updateMusics(youtubeChannel, maxResults);
            musics.addAll(updatedMusics);
        });
        return musics;
    }

    private List<Video> updateMusics(YoutubeChannel youtubeChannel,
                                     Integer maxResults) {
        List<Video> musics = new ArrayList<>();

        youtubeChannel.getMusicPlaylistIds().forEach(playlistId -> {
            apiUtils.executeWithHandling(() -> {
                List<String> musicIds = playlistItemFetcher.fetchPlaylistItem(playlistId, maxResults);
                List<Video> fetchedMusics = videoFetcher.fetchVideos(musicIds, VideoType.MUSIC);
                List<Video> updatedVideos = updateVideos(fetchedMusics);

                log.info("Updated Musics={}", updatedVideos);
                musics.addAll(updatedVideos);
                return null;
            });
        });

        return musics;
    }

    private List<Video> fetchVideos(List<String> videoIds,
                                    VideoType videoType) {
        videoIds.forEach(videoId -> {
            if(isNewVideo(videoId)) {
                Video video = videoFetcher.fetchVideo(videoId, videoType);
                videoRepo.save(video);
            }
        })
    }
}

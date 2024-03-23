package com.stellive.fansite.service;

import com.stellive.fansite.client.PlaylistItemsClient;
import com.stellive.fansite.client.VideoClient;
import com.stellive.fansite.domain.Video;
import com.stellive.fansite.domain.VideoType;
import com.stellive.fansite.domain.YoutubeChannel;
import com.stellive.fansite.repository.Video.VideoRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class VideoService {

    private final PlaylistItemsClient playlistItemsClient;
    private final VideoClient videoClient;
    private final VideoRepo videoRepo;

    public List<Video> findByChannelIdAndVideoType(Long id, VideoType videoType) {

        return videoRepo.findByChannelIdAndVideoType(id, videoType);
    }

    public List<Video> updateVideos(Integer maxResults) {

        log.info("Update Videos");
        List<Video> videos = new ArrayList<>();

        Arrays.stream(YoutubeChannel.values())
                .forEach(youtubeChannel -> {
                    List<Video> fetchedVideos = playlistItemsClient
                            .getVideosFromPlaylistId(getPlaylistFromId(youtubeChannel), maxResults);
                    fetchedVideos = videoClient.setDuration(fetchedVideos);

                    List<Video> determinedVideos = determineVideoType(youtubeChannel, fetchedVideos);
                    List<Video> updatedVideos = videoRepo.save(determinedVideos);
                    log.info("Updated Videos={}", updatedVideos);

                    videos.addAll(updatedVideos);
                });
        return videos;
    }

    private List<Video> determineVideoType(YoutubeChannel youtubeChannel,
                                           List<Video> fetchedVideos) {

        if (youtubeChannel.getId() % 10 == 2) {
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

    private String getPlaylistFromId(YoutubeChannel youtubeChannel) {

        StringBuilder builder = new StringBuilder(youtubeChannel.getChannelId());
        builder.setCharAt(1, 'U');
        return builder.toString();
    }

    public List<Video> updateMusics(Integer maxResults) {

        log.info("Update Musics");
        List<Video> videos = new ArrayList<>();

        Arrays.stream(YoutubeChannel.values())
                .forEach(youtubeChannel ->
                        updateMusics(youtubeChannel, maxResults));
        return videos;
    }

    private List<Video> updateMusics(YoutubeChannel youtubeChannel,
                                     Integer maxResults) {

        List<Video> videos = new ArrayList<>();

        List<String> musicPlaylistIds = youtubeChannel.getMusicPlaylistIds();
        musicPlaylistIds.forEach(playlistId -> {
            List<Video> fetchedVideos = playlistItemsClient
                    .getVideosFromPlaylistId(playlistId, maxResults);

            fetchedVideos.forEach(video -> video.setVideoType(VideoType.MUSIC));
            List<Video> determinedVideos = videoClient.setDuration(fetchedVideos);

            List<Video> updatedVideos = videoRepo.save(determinedVideos);
            log.info("Updated Musics={}", updatedVideos);

            videos.addAll(updatedVideos);
        });

        return videos;
    }
}

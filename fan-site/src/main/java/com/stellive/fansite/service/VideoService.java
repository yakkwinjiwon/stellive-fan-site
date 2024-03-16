package com.stellive.fansite.service;

import com.stellive.fansite.client.MusicClient;
import com.stellive.fansite.client.VideoClient;
import com.stellive.fansite.domain.Stella;
import com.stellive.fansite.domain.VideoType;
import com.stellive.fansite.domain.Video;
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

    private final VideoClient videoClient;
    private final MusicClient musicClient;
    private final VideoRepo videoRepo;

    public List<Video> findByChannelIdAndVideoType(Long id, VideoType videoType) {
        return videoRepo.findByChannelIdAndVideoType(id, videoType);
    }

    public List<Video> updateAllVideos() {
        List<Video> videos = new ArrayList<>();
        Arrays.stream(Stella.values())
                .forEach(stella ->{
                    List<Video> updatedVideos = updateVideos(stella);
                    videos.addAll(updatedVideos);
                });
        return videos;
    }

    public List<Video> updateVideos(Stella stella) {
        List<Video> videos = videoClient.getVideos(stella, 2);
        log.info("updateVideos={}", videos);
        return videoRepo.save(videos);
    }

    public List<Video> updateAllMusics() {
        List<Video> musics = new ArrayList<>();
        Arrays.stream(Stella.values())
                .forEach(stella ->{
                    List<Video> updatedMusics = updateMusics(stella);
                    musics.addAll(updatedMusics);
                });
        log.info("updateAllMusics={}", musics);
        return musics;
    }

    private List<Video> updateMusics(Stella stella) {
        List<Video> musics = new ArrayList<>();
        stella.getMusicPlaylistIds()
                .forEach(PlaylistId -> {
                    List<Video> fetchedMusics = musicClient.getMusics(stella, PlaylistId);
                    musics.addAll(fetchedMusics);
                });
        log.info("updateMusics={}", musics);
        return musics;
    }
}

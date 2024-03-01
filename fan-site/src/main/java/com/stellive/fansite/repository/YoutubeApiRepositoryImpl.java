package com.stellive.fansite.repository;

import com.stellive.fansite.domain.Video;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class YoutubeApiRepositoryImpl implements YoutubeApiRepository{

    private final SpringDataJpaYoutubeApiRepository springDataJpaRepository;

    @Override
    public List<Video> saveChannelVideos(List<Video> videos) {
        return springDataJpaRepository.saveAll(videos);
    }

    @Override
    public List<Video> saveAllVideos(List<Video> videos) {
        return null;
    }
}

package com.stellive.fansite.repository.Video;

import com.stellive.fansite.domain.VideoType;
import com.stellive.fansite.domain.Video;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Slf4j
public class VideoRepoImpl implements VideoRepo {

    private final VideoDataRepo videoDataRepo;

    @Override
    public List<Video> save(List<Video> videos) {
        return videoDataRepo.saveAll(videos);
    }

    @Override
    public List<Video> findByChannelId(Long channelId) {
        return videoDataRepo.findByChannelId(channelId);
    }

    @Override
    public List<Video> findByChannelIdAndVideoType(Long channelId, VideoType videoType) {
        return videoDataRepo.findByChannelIdAndVideoType(channelId, videoType);
    }

    @Override
    public Optional<Video> findByExternalId(String externalId) {
        return videoDataRepo.findByExternalId(externalId);
    }
}

package com.stellive.fansite.repository;

import com.stellive.fansite.domain.YTUser;
import com.stellive.fansite.domain.YTVideo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class YTDataRepository implements YTRepository {

    private final YTUserDataRepository userRepository;
    private final YTVideoDataRepository videoRepository;

    @Override
    public YTUser saveYTUser(YTUser channel) {
        return userRepository.save(channel);
    }

    @Override
    public Optional<YTUser> findYTUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public List<YTUser> findAllYTUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<YTVideo> saveYTVideos(List<YTVideo> videos) {
        return videoRepository.saveAll(videos);
    }

    @Override
    public List<YTVideo> findYTVideosByYTUserId(Long id) {
        return videoRepository.findAllByUserId(id);
    }


}

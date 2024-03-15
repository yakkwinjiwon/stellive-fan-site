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
public class YTDataRepo implements YTRepo {

    private final YTUserDataRepo userRepo;
    private final YTVideoDataRepo videoRepo;

    @Override
    public YTUser saveYTUser(YTUser channel) {
        return userRepo.save(channel);
    }

    @Override
    public Optional<YTUser> findYTUserById(Long id) {
        return userRepo.findById(id);
    }

    @Override
    public List<YTUser> findAllYTUsers() {
        return userRepo.findAll();
    }

    @Override
    public List<YTVideo> saveYTVideos(List<YTVideo> videos) {
        return videoRepo.saveAll(videos);
    }

    @Override
    public List<YTVideo> findYTVideosByYTUserId(Long id) {
        return videoRepo.findAllByUserId(id);
    }


}

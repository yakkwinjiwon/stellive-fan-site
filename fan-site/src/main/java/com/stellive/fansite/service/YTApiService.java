package com.stellive.fansite.service;

import com.stellive.fansite.client.YTUserClient;
import com.stellive.fansite.client.YTVideoClient;
import com.stellive.fansite.domain.YTUser;
import com.stellive.fansite.domain.Stella;
import com.stellive.fansite.domain.YTVideo;
import com.stellive.fansite.repository.YTRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class YTApiService {

    private final YTUserClient userClient;
    private final YTVideoClient videoClient;
    private final YTRepository repository;

    public String test(){


        return "ok";
    }

    public void updateAll(){
        updateAllYTUsers();
    }

    public YTUser updateYTUser(Stella stella) {
        YTUser channel = userClient.getYTUser(stella);
        return repository.saveYTUser(channel);
    }

    public void updateAllYTUsers() {
        Arrays.stream(Stella.values())
                .forEach(this::updateYTUser);
    }

    public YTUser findYTUserById(Long id) {
        return repository.findYTUserById(id).orElseGet(YTUser::new);
    }

    public List<YTUser> findAllYTUsers() {
        return repository.findAllYTUsers();
    }

    public List<YTVideo> updateYTVideos(Stella stella) {
        List<YTVideo> videos = videoClient.getYTVideos(stella, 2);
        return repository.saveYTVideos(videos);
    }

    public void updateAllYTVideos() {
        Arrays.stream(Stella.values())
                .forEach(this::updateYTVideos);
    }

    public List<YTVideo> findYTVideosByYTUserId(Long id) {
        return repository.findYTVideosByYTUserId(id);
    }

}

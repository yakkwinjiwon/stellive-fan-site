package com.stellive.fansite.service;

import com.stellive.fansite.client.YoutubeChannelClient;
import com.stellive.fansite.client.YoutubeVideoClient;
import com.stellive.fansite.domain.Channel;
import com.stellive.fansite.domain.ChannelId;
import com.stellive.fansite.domain.Video;
import com.stellive.fansite.repository.YoutubeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class YoutubeApiService {

    private final YoutubeChannelClient channelClient;
    private final YoutubeVideoClient videoClient;
    private final YoutubeRepository repository;

    public String test(){
//        Channel channel = apiClient.getChannel(MASHIRO);
//        log.info("channel={}", channel);
//        repository.saveChannel(channel);
//
//        List<Video> videos = apiClient.getVideos(MASHIRO, 2);
//        log.info("videos={}", videos);
//        repository.saveVideos(videos);
//
//        List<Video> findVideos = repository.findVideosByChannelId(MASHIRO.getId());
//        log.info("findVideos={}", findVideos);

        return "ok";
    }

    public void updateAll(){
        updateAllChannels();
    }

    public void updateAllChannels() {
        Arrays.stream(ChannelId.values())
                .forEach(this::updateChannel);
    }
    public Channel updateChannel(ChannelId channelId) {
        Channel channel = channelClient.getChannel(channelId);
        return repository.saveChannel(channel);
    }

    public void updateAllVideos() {
        Arrays.stream(ChannelId.values())
                .forEach(this::updateVideos);
    }
    public List<Video> updateVideos(ChannelId channelId) {
        List<Video> videos = videoClient.getVideos(channelId, 2);
        return repository.saveVideos(videos);
    }

    public Channel findChannelById(Long id) {
        return repository.findChannelById(id).orElseGet(Channel::new);
    }

    public List<Video> findVideosByChannelId(Long id) {
        return repository.findVideosByChannelId(id);
    }

    public List<Channel> findAllChannels() {
        return repository.findAllChannels();
    }
}

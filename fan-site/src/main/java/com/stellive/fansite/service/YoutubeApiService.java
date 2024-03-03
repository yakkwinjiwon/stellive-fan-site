package com.stellive.fansite.service;

import com.stellive.fansite.client.YoutubeApiClient;
import com.stellive.fansite.domain.Channel;
import com.stellive.fansite.domain.ChannelId;
import com.stellive.fansite.domain.Video;
import com.stellive.fansite.repository.YoutubeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

import static com.stellive.fansite.domain.ChannelId.*;
import static com.stellive.fansite.utils.YoutubeApiConst.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class YoutubeApiService {

    private final YoutubeApiClient apiClient;
    private final YoutubeRepository repository;

    public String test(){
        Channel channel = apiClient.getChannel(MASHIRO);
        log.info("channel={}", channel);
        repository.saveChannel(channel);

        List<Video> videos = apiClient.getVideos(MASHIRO, 2);
        log.info("videos={}", videos);
        repository.saveVideos(videos);

        List<Video> findVideos = repository.findVideosByChannelId(1L);
        log.info("findVideos={}", findVideos);

        return "ok";
    }
    public Channel updateChannel(ChannelId channelId) {
        Channel channel = apiClient.getChannel(channelId);
        return repository.saveChannel(channel);
    }

    public void updateAll(){

    }
    public void updateAllChannels() {
        Arrays.stream(ChannelId.values())
                        .forEach(this::updateChannel);
    }

    public Channel findChannelById(Long id) {
        return repository.findChannelById(id).orElseGet(Channel::new);
    }
    public Channel findChannelByExternalId(String externalId) {
        return repository.findChannelByExternalId(externalId).orElseGet(Channel::new);
    }

    public void updateChannelVideos(String channelId) {

    }
}

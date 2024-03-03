package com.stellive.fansite.service;

import com.stellive.fansite.client.YoutubeApiClient;
import com.stellive.fansite.domain.Channel;
import com.stellive.fansite.domain.Video;
import com.stellive.fansite.repository.YoutubeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
        Channel channel = apiClient.getChannel(CHANNEL_ID_MASHIRO);
        log.info("channel={}", channel);
        repository.saveChannel(channel);

        List<Video> videos = apiClient.getVideos(CHANNEL_ID_MASHIRO, 2);
        log.info("videos={}", videos);
        repository.saveVideos(videos);

        List<Video> findVideos = repository.findVideosByChannelId(1L);
        log.info("findVideos={}", findVideos);

        return "ok";
    }
    public Channel updateChannel(String channelId) {
        Channel channel = apiClient.getChannel(channelId);
        return repository.saveChannel(channel);
    }

    public void updateAll(){

    }
    public void updateAllChannels() {
        for (int i = 1; i <= values().length; i++) {
            updateChannel(findExternalIdById())
        }
        updateChannel(KANNA.getExternalId());
        updateChannel(YUNI.getExternalId());
        updateChannel(HINA.getExternalId());
        updateChannel(MASHIRO.getExternalId());
        updateChannel(LIZE.getExternalId());
        updateChannel(TABI.getExternalId());
        updateChannel(OFFICIAL.getExternalId());
    }

    public Channel findChannelById(Long id) {
        return repository.findChannelById(id).orElseGet(Channel::new);
    }

    public void updateChannelVideos(String channelId) {
        List<Video> videos = apiClient.getVideos(channelId, 8);

    }
}

package com.stellive.fansite.service;

import com.stellive.fansite.client.YoutubeApiClient;
import com.stellive.fansite.domain.Channel;
import com.stellive.fansite.domain.Video;
import com.stellive.fansite.repository.YoutubeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.stellive.fansite.utils.YoutubeApiConst.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class YoutubeApiService {

    private final YoutubeApiClient apiClient;
    private final YoutubeRepository repository;

    public String test(){
        apiClient.getChannel(CHANNEL_ID_MASHIRO);
        apiClient.getChannelVideos(CHANNEL_ID_OFFICIAL, 2);
        return "ok";
    }
    public Channel updateChannel(String channelId) {
        Channel channel = apiClient.getChannel(channelId);
        return repository.saveChannel(channel);
    }

    public void updateAll(){

    }
    public void updateAllChannels() {
        updateChannel(CHANNEL_ID_KANNA);
        updateChannel(CHANNEL_ID_YUNI);
        updateChannel(CHANNEL_ID_HINA);
        updateChannel(CHANNEL_ID_MASHIRO);
        updateChannel(CHANNEL_ID_LIZE);
        updateChannel(CHANNEL_ID_TABI);
        updateChannel(CHANNEL_ID_OFFICIAL);
    }

    public Channel findChannelById(Long id) {
        return repository.findChannelById(id).orElseGet(Channel::new);
    }

    public void updateChannelVideos(String channelId) {
        List<Video> videos = apiClient.getChannelVideos(channelId, 8);

    }
}

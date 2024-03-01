package com.stellive.fansite.service;

import com.stellive.fansite.client.YoutubeApiClient;
import com.stellive.fansite.domain.Video;
import com.stellive.fansite.repository.YoutubeApiRepository;
import com.stellive.fansite.utils.YoutubeApiConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.stellive.fansite.utils.YoutubeApiConst.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class YoutubeApiService {

    private final YoutubeApiClient apiClient;
    private final YoutubeApiRepository repository;

    public String test(){
        apiClient.getChannelVideos(CHANNEL_ID_OFFICIAL, 2);
        return "ok";
    }

    public void updateChannelVideos(String channelId) {
        List<Video> videos = apiClient.getChannelVideos(channelId, 8);

    }
}

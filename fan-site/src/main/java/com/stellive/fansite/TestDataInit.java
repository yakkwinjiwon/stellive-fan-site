package com.stellive.fansite;

import com.stellive.fansite.domain.Channel;
import com.stellive.fansite.domain.ChannelId;
import com.stellive.fansite.service.YoutubeApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@Slf4j
@RequiredArgsConstructor
public class TestDataInit {

    private final YoutubeApiService youtubeApiService;

    @EventListener(ApplicationReadyEvent.class)
    public void initData() {
        log.info("init data");
        youtubeApiService.updateAllChannels();
        log.info("Updated All Channels");
        youtubeApiService.updateAllVideos();
        log.info("Updated All Videos");

    }

}

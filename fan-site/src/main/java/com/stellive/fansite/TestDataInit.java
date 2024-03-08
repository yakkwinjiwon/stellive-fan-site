package com.stellive.fansite;

import com.stellive.fansite.service.YTApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@Slf4j
@RequiredArgsConstructor
public class TestDataInit {

    private final YTApiService youtubeApiService;

    @EventListener(ApplicationReadyEvent.class)
    public void initData() {
        log.info("init data");
        youtubeApiService.updateAllYTUsers();
        log.info("Updated All Channels");
        youtubeApiService.updateAllYTVideos();
        log.info("Updated All Videos");

    }

}

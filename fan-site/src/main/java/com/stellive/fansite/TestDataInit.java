package com.stellive.fansite;

import com.stellive.fansite.service.scheduler.ChannelScheduler;
import com.stellive.fansite.service.scheduler.NewsScheduler;
import com.stellive.fansite.service.scheduler.VideoScheduler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class TestDataInit {

    private final ChannelScheduler channelService;
    private final VideoScheduler videoService;
    private final NewsScheduler noticeService;

//    @EventListener(ApplicationReadyEvent.class)
//    public void initData() {
//        log.info("init data");
//        channelService.updateAll();
//        log.info("Updated All Channels");
//        videoService.updateVideos();
//        log.info("Updated All Videos");
//        videoService.updateMusics();
//        log.info("Updated All Musics");
//        noticeService.updateNotices();
//        log.info("Updated Notices");
//    }

}

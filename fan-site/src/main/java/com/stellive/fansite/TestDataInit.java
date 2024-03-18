package com.stellive.fansite;

import com.stellive.fansite.service.ChannelService;
import com.stellive.fansite.service.NoticeService;
import com.stellive.fansite.service.VideoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@Slf4j
@RequiredArgsConstructor
public class TestDataInit {

    private final ChannelService channelService;
    private final VideoService videoService;
    private final NoticeService noticeService;

    @EventListener(ApplicationReadyEvent.class)
    public void initData() {
        log.info("init data");
        channelService.updateAll();
        log.info("Updated All Channels");
        videoService.updateAllVideos();
        log.info("Updated All Videos");
        videoService.updateAllMusics();
        log.info("Updated All Musics");
        noticeService.updateNotices();
        log.info("Updated Notices");
    }

}

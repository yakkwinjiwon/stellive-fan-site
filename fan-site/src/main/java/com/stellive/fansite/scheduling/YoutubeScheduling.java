package com.stellive.fansite.scheduling;

import com.stellive.fansite.service.ChannelService;
import com.stellive.fansite.service.VideoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import static com.stellive.fansite.utils.YoutubeApiConst.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class YoutubeScheduling {

    private final ChannelService channelService;
    private final VideoService videoService;


    /**
     *  데이터 전부 갱신
     */
    public void updateAll() {
        log.info("Update all");
        channelService.updateAll();
        videoService.updateVideos(MAX_RESULTS_ALL);
        videoService.updateMusics(MAX_RESULTS_ALL);
    }

    @Scheduled(cron = "* */10 * * * ?")
    public void updateRecent() {
        log.info("Update recent");
        videoService.updateVideos(MAX_RESULTS_VIDEO);
        videoService.updateMusics(MAX_RESULTS_MUSIC);
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void updateChannel() {
        log.info("Update channel");
        channelService.updateAll();
    }
}

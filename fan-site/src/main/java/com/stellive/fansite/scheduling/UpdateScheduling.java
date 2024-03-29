package com.stellive.fansite.scheduling;

import com.stellive.fansite.service.ChannelService;
import com.stellive.fansite.service.NewsService;
import com.stellive.fansite.service.VideoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import static com.stellive.fansite.utils.YoutubeApiConst.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class UpdateScheduling {

    private final ChannelService channelService;
    private final VideoService videoService;
    private final NewsService newsService;


    /**
     *  데이터 전부 갱신
     */
    public void updateAll() {
        log.info("Update all");
        channelService.updateChannels();
        videoService.updateVideos(MAX_RESULTS_ALL);
        videoService.updateMusics(MAX_RESULTS_ALL);
        newsService.updateNotices();
    }

    @Scheduled(cron = "*/10 * * * * ?")
//    @Scheduled(cron = "* */10 * * * ?")
    public void updateRecent() {
        log.info("Update recent");
        channelService.updateChannels();
        videoService.updateVideos(MAX_RESULTS_VIDEO);
        videoService.updateMusics(MAX_RESULTS_MUSIC);
        newsService.updateNotices();
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void updateChannel() {
        log.info("Update channel");
        channelService.updateChannels();
    }
}

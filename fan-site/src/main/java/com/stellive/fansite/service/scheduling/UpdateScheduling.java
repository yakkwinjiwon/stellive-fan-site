package com.stellive.fansite.service.scheduling;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import static com.stellive.fansite.utils.ApiConst.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class UpdateScheduling {

    private final ChannelScheduler channelScheduler;
    private final VideoScheduler videoScheduler;
    private final NewsScheduler newsScheduler;

    /**
     *  데이터 전부 갱신
     */
    public void updateAll() {
        log.info("Update all");
        channelScheduler.updateChannels();
        videoScheduler.updateVideos(MAX_RESULTS_ALL);
    }

    @Scheduled(cron = "*/10 * * * * ?")
//    @Scheduled(cron = "* */10 * * * ?")
    public void updateRecent() {
        log.info("Update recent");
        channelScheduler.updateChannels();
        videoScheduler.updateVideos(MAX_RESULTS_VIDEO);
    }

//    @Scheduled(cron = "0 0 0 * * ?")
    public void updateChannel() {
        log.info("Update channel");
        channelScheduler.updateChannels();
    }
}

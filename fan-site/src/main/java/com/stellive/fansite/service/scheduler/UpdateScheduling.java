package com.stellive.fansite.service.scheduler;

import com.stellive.fansite.utils.ScraperUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
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
    private final MusicScheduler musicScheduler;
    private final LiveScheduler liveScheduler;

    /**
     *  데이터 전부 갱신
     */
    public void updateAll() {
        log.info("Update all");
        channelScheduler.updateChannels();
        videoScheduler.updateVideos(MAX_RESULTS_ALL);

    }

    @Scheduled(cron = "*/5 * * * * ?")
//    @Scheduled(cron = "* */10 * * * ?")
    public void updateRecent() {
        log.info("Update recent");
        channelScheduler.updateChannels();
//        videoScheduler.updateVideos(MAX_RESULTS_VIDEO);

        ChromeDriver driver = ScraperUtils.getDriver();
        WebDriverWait wait = ScraperUtils.getWait(driver);
//        newsScheduler.updateNotices(driver, wait);
//        musicScheduler.updateMusics(driver, wait, ScraperConst.MUSIC_LIMIT);
//        liveScheduler.updateLives(driver, wait);
        driver.quit();

    }

//    @Scheduled(cron = "0 0 0 * * ?")
    public void updateChannel() {
        log.info("Update channel");
        channelScheduler.updateChannels();
    }
}

package com.stellive.fansite.service.initializer;

import com.stellive.fansite.service.scheduler.*;
import com.stellive.fansite.utils.ScraperConst;
import com.stellive.fansite.utils.ScraperUtils;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import static com.stellive.fansite.utils.ApiConst.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class DataUpdater {

    private final ChannelScheduler channelScheduler;
    private final VideoScheduler videoScheduler;
    private final NewsScheduler newsScheduler;
    private final MusicScheduler musicScheduler;
    private final LiveScheduler liveScheduler;

    @Scheduled(cron = "*/10 * * * * ?")
    public void updateRecent() {
        log.info("Update recent");

        channelScheduler.updateChannels();
        videoScheduler.updateVideos(MAX_RESULTS_VIDEO);

        WebDriver driver = ScraperUtils.getDriver();
        WebDriverWait wait = ScraperUtils.getWait(driver);
        newsScheduler.updateNews(driver, wait, ScraperConst.NEWS_LIMIT);
        musicScheduler.updateMusics(driver, wait, ScraperConst.MUSIC_LIMIT);
        liveScheduler.updateLives(driver, wait);
        driver.quit();
    }
}

package com.stellive.fansite.service.initializer;

import com.stellive.fansite.service.scheduler.*;
import com.stellive.fansite.utils.ApiConst;
import com.stellive.fansite.utils.ScraperConst;
import com.stellive.fansite.utils.ScraperUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class DataInitializer {

    private final ChannelScheduler channelScheduler;
    private final LiveScheduler liveScheduler;
    private final MusicScheduler musicScheduler;
    private final NewsScheduler newsScheduler;
    private final VideoScheduler videoScheduler;

    @EventListener(ApplicationReadyEvent.class)
    public void initAll() {
        log.info("Initialize");

        channelScheduler.updateChannels();
        videoScheduler.updateVideos(ApiConst.MAX_RESULTS_ALL);

        WebDriver driver = ScraperUtils.getDriver();
        WebDriverWait wait = ScraperUtils.getWait(driver);
        newsScheduler.updateNews(driver, wait, ScraperConst.NEWS_ALL);
        musicScheduler.updateMusics(driver, wait, ScraperConst.MUSIC_ALL);
        liveScheduler.updateLives(driver, wait);
        driver.quit();
    }
}

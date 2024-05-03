package com.stellive.fansite.service.scheduler;

import com.stellive.fansite.domain.Live;
import com.stellive.fansite.repository.Live.LiveRepo;
import com.stellive.fansite.scraper.LiveScraper;
import com.stellive.fansite.utils.ScraperUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class LiveScheduler {

    private final LiveScraper liveScraper;

    private final LiveRepo liveRepo;

    public List<Live> updateLives(ChromeDriver driver,
                                  WebDriverWait wait) {
        log.info("update Lives");
        List<Live> scrapedLives = new ArrayList<>();

        ScraperUtils.executeForEachChannel(chzzkChannel -> {
            scrapedLives.add(liveScraper.scrapeLive(driver, wait, chzzkChannel));
        });
        log.info("scraped Lives={}", scrapedLives);

        List<Live> updatedLives = liveRepo.saveAll(scrapedLives);
        log.info("updated Lives={}", updatedLives);

        return updatedLives;
    }
}

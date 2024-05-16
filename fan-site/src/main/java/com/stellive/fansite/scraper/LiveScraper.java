package com.stellive.fansite.scraper;

import com.stellive.fansite.domain.ChzzkChannel;
import com.stellive.fansite.domain.Live;
import com.stellive.fansite.repository.Channel.ChannelRepo;
import com.stellive.fansite.utils.ScraperUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.stellive.fansite.utils.ScraperConst.*;

@Service
@RequiredArgsConstructor
public class LiveScraper {

    private final ChannelRepo channelRepo;

    public Live scrapeLive(WebDriver driver,
                           WebDriverWait wait,
                           ChzzkChannel chzzkChannel) {
        driver.get(chzzkChannel.getUrl());

        Live.LiveBuilder builder = Live.builder()
                .id(chzzkChannel.getId())
                .channel(channelRepo.findById(chzzkChannel.getId()).orElse(null));

        wait.until(webDriver -> webDriver.findElement(By.cssSelector(CSS_SELECTOR_LIVE_WAIT)));
        try {
            driver.findElement(By.cssSelector(CSS_SELECTOR_LIVE_BADGE));
            return builder.isLive(true).build();

        } catch (NoSuchElementException e) {
            return builder.isLive(false).build();
        }
    }
}

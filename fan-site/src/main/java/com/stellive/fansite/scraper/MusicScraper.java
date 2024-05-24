package com.stellive.fansite.scraper;

import com.stellive.fansite.utils.AppConst;
import com.stellive.fansite.utils.ScraperUtils;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.regex.Matcher;

import static com.stellive.fansite.utils.ApiConst.*;
import static com.stellive.fansite.utils.ApiConst.MAX_DELAY;
import static com.stellive.fansite.utils.AppConst.*;
import static com.stellive.fansite.utils.ScraperConst.*;

@Service
public class MusicScraper {

    @Retryable(value = {TimeoutException.class}, maxAttempts = MAX_ATTEMPTS,
            backoff = @Backoff(delay = DELAY, multiplier = MULTIPLIER, maxDelay = MAX_DELAY))
    public List<String> scrapeMusicIds(WebDriver driver,
                                       WebDriverWait wait,
                                       Integer limit) {
        driver.get(URL_MUSIC);

        wait.until(webDriver -> webDriver.findElement(By.cssSelector(CSS_SELECTOR_MUSIC_MORE)));
        driver.findElement(By.cssSelector(CSS_SELECTOR_MUSIC_MORE)).click();

        wait.until(webDriver -> webDriver.findElement(By.cssSelector(CSS_SELECTOR_MUSIC_LIST)));
        List<WebElement> musicElements = driver.findElements(By.cssSelector(CSS_SELECTOR_MUSIC_LIST));

        return musicElements.stream()
                .limit(limit)
                .map(element -> scrapeMusicId(driver, wait, element))
                .toList();
    }

    private String scrapeMusicId(WebDriver driver,
                                 WebDriverWait wait,
                                 WebElement element) {
        element.sendKeys(Keys.CONTROL, Keys.RETURN);

        ScraperUtils.switchToLatestTab(driver);

        wait.until(webDriver -> webDriver.findElement(By.cssSelector(CSS_SELECTOR_MUSIC_LINK)));
        String url = driver.findElement(By.cssSelector(CSS_SELECTOR_MUSIC_LINK))
                .getAttribute(ATTRIBUTE_SRC);

        driver.close();
        ScraperUtils.switchToLatestTab(driver);

        return extractMusicId(url);
    }

    private String extractMusicId(String url) {
        Matcher matcher = PATTERN_MUSIC_ID.matcher(url);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return STRING_DEFAULT;
    }
}

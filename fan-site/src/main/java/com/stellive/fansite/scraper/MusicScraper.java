package com.stellive.fansite.scraper;

import com.stellive.fansite.exceptions.ScraperException;
import com.stellive.fansite.utils.ScraperUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;

import static com.stellive.fansite.utils.ScraperConst.*;

@Service
@Slf4j
public class MusicScraper {

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

        wait.until(webDriver -> webDriver.findElement(By.cssSelector(CSS_SELECTOR_MUSIC_IMG)));
        String url = driver.findElement(By.cssSelector(CSS_SELECTOR_MUSIC_IMG))
                .getAttribute(ATTRIBUTE_SRC);
        log.info("Music URL: {}", url);

        driver.close();
        ScraperUtils.switchToLatestTab(driver);
        return extractMusicId(url);
    }

    private String extractMusicId(String url) {
        Matcher matcher = PATTERN_MUSIC_ID.matcher(url);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return "";
    }
}

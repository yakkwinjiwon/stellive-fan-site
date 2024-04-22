package com.stellive.fansite.scraper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import static com.stellive.fansite.utils.ScraperConst.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class MusicScraper {

    public List<String> scrapeMusicIds(ChromeDriver driver,
                                       WebDriverWait wait) {
        driver.get(URL_MUSIC);
        return parseMusic(driver, wait);
    }

    private List<String> parseMusic(ChromeDriver driver,
                                    WebDriverWait wait) {
        wait.until(webDriver -> webDriver.findElement(By.cssSelector(CSS_SELECTOR_MUSIC_LIST)));
        List<WebElement> musicElements = driver.findElements(By.cssSelector(CSS_SELECTOR_MUSIC_LIST));

        return musicElements.stream()
                .map(element -> scrapeMusicId(driver, wait, element))
                .toList();
    }

    private String scrapeMusicId(ChromeDriver driver,
                                 WebDriverWait wait,
                                 WebElement element) {
        element.sendKeys(Keys.CONTROL, Keys.RETURN);
        List<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.getLast());

        wait.until(webDriver -> webDriver.findElement(By.cssSelector(CSS_SELECTOR_MUSIC_IMG)));
        String url = driver.findElement(By.cssSelector(CSS_SELECTOR_MUSIC_IMG))
                .getAttribute(ATTRIBUTE_SRC);

        driver.close();
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
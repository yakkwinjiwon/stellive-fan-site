package com.stellive.fansite.scraper;

import com.stellive.fansite.api.Youtube.VideoFetcher;
import com.stellive.fansite.domain.News;
import com.stellive.fansite.domain.Video;
import com.stellive.fansite.domain.VideoType;
import com.stellive.fansite.utils.AppUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.stellive.fansite.utils.ScraperConst.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class MusicScraper {

    private final VideoFetcher videoFetcher;

    public List<Video> scrapeMusic(ChromeDriver driver,
                                   WebDriverWait wait) {
        driver.get(URL_MUSIC);
        wait.until(webDriver -> webDriver.findElement(By.cssSelector(CSS_SELECTOR_MUSIC_MORE)));
        driver.findElement(By.cssSelector(CSS_SELECTOR_MUSIC_MORE)).click();
        List<String> musicIds = parseMusic(driver, wait);
        return videoFetcher.fetchVideos(musicIds, VideoType.MUSIC);
    }

    private List<String> parseMusic(ChromeDriver driver,
                                    WebDriverWait wait) {
        wait.until(webDriver -> webDriver.findElement(By.cssSelector(CSS_SELECTOR_MUSIC)));
        List<WebElement> musicElements = driver.findElements(By.cssSelector(CSS_SELECTOR_MUSIC));

        return musicElements.stream()
                .map(element -> scrapeMusicId(element, wait))
                .toList();
    }

    private String scrapeMusicId(WebElement element,
                                 WebDriverWait wait) {
        element.click();
        wait.until(webDriver -> webDriver.findElement(By.cssSelector(CSS_SELECTOR_MUSIC_LINK)));
        String url = element.findElement(By.cssSelector(CSS_SELECTOR_MUSIC_LINK)).getAttribute(ATTRIBUTE_HREF);
        return extractMusicId(url);
    }

    private String extractMusicId(String url) {
        try {
            String query = new URI(url).getQuery();
            List<String> params = Arrays.asList(query.split("&"));

            return params.stream()
                    .filter(param -> param.startsWith("v="))
                    .findFirst()
                    .map(p -> p.substring(2))
                    .orElseThrow(() -> new RuntimeException("No video id found"));
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}

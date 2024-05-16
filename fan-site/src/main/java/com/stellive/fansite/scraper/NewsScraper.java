package com.stellive.fansite.scraper;

import com.stellive.fansite.domain.News;
import com.stellive.fansite.exceptions.ScraperException;
import com.stellive.fansite.utils.AppUtils;
import com.stellive.fansite.utils.ScraperUtils;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static com.stellive.fansite.utils.ScraperConst.*;

@Service
@Slf4j
public class NewsScraper {

    public List<News> scrapeNews(WebDriver driver,
                                 WebDriverWait wait,
                                 Integer limit) {
        driver.get(URL_NEWS);

        wait.until(webDriver -> webDriver.findElement(By.cssSelector(CSS_SELECTOR_NEWS_MORE)));
        driver.findElement(By.cssSelector(CSS_SELECTOR_NEWS_MORE)).click();

        wait.until(webDriver -> webDriver.findElement(By.cssSelector(CSS_SELECTOR_NEWS_LIST)));
        List<WebElement> newsElements = driver.findElements(By.cssSelector(CSS_SELECTOR_NEWS_LIST));

        return newsElements.stream()
                .limit(limit)
                .map(this::buildNews)
                .toList();
    }

    private News buildNews(WebElement element) {
        String url = element.findElement(By.cssSelector(CSS_SELECTOR_NEWS_URL)).getAttribute(ATTRIBUTE_HREF);
        String imgUrl = element.findElement(By.cssSelector(CSS_SELECTOR_NEWS_IMG_URL)).getAttribute(ATTRIBUTE_SRC);
        String title = element.findElement(By.cssSelector(CSS_SELECTOR_NEWS_TITLE)).getText();
        String publishTimeString = element.findElement(By.cssSelector(CSS_SELECTOR_NEWS_PUBLISH_TIME)).getText();
        Instant publishTime = AppUtils.stringToInstant(publishTimeString, "yyyy.MM.dd");
        return News.builder()
                .url(url)
                .imgUrl(imgUrl)
                .title(title)
                .publishTime(publishTime)
                .build();
    }

}

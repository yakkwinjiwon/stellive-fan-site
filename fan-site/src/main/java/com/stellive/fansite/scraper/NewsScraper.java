package com.stellive.fansite.scraper;

import com.stellive.fansite.domain.News;
import com.stellive.fansite.exceptions.ScraperException;
import com.stellive.fansite.utils.AppUtils;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;

import static com.stellive.fansite.utils.ScraperConst.*;

@Service
@Slf4j
public class NewsScraper {

    public List<News> scrapeNews(ChromeDriver driver, WebDriverWait wait) {
        try {
            driver.get(URL_NEWS);
            return parseNews(driver, wait);
        } catch (NoSuchElementException e) {
            throw new ScraperException("News not found", e);
        } catch (TimeoutException e){
            throw new ScraperException("News time out", e);
        } catch (JavascriptException e){
            throw new ScraperException("News javascript error", e);
        } catch (Exception e){
            throw new ScraperException("News unknown error", e);
        }
    }

    private List<News> parseNews(ChromeDriver driver,
                                 WebDriverWait wait) {
        wait.until(webDriver -> webDriver.findElement(By.cssSelector(CSS_SELECTOR_NEWS_LIST)));
        List<WebElement> newsElements = driver.findElements(By.cssSelector(CSS_SELECTOR_NEWS_LIST));

        return newsElements.stream()
                .limit(NEWS_LIMIT)
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

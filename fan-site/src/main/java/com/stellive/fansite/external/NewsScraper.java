package com.stellive.fansite.external;

import com.stellive.fansite.domain.News;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static com.stellive.fansite.utils.WebScraperConst.*;

@Service
@Slf4j
public class NewsScraper {

    public List<News> getNews() {
        ChromeDriver driver = getDriver();

        try {
            driver.get(URL_NEWS);

            new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(webDriver -> webDriver.findElement(By.cssSelector(CSS_SELECTOR_NEWS)));
            return parseElement(driver);
        } finally {
            if (driver != null) {
                driver.quit();
            }
        }
    }

    private List<News> parseElement(ChromeDriver driver) {
        ArrayList<News> news = new ArrayList<>();
        List<WebElement> newsElements = driver.findElements(By.cssSelector(CSS_SELECTOR_NEWS));
        newsElements.stream()
                .limit(NEWS_LIMIT)
                .forEach(element -> news.add(buildNotice(element)));
        return news;
    }

    private News buildNotice(WebElement element) {
        String url = element.findElement(By.cssSelector(".bh_img_content a")).getAttribute("href");
        String imgUrl = element.findElement(By.cssSelector(".bh_img_content a img")).getAttribute("src");
        String title = element.findElement(By.cssSelector(".bh_title.ff-nn .title span")).getText();
        Instant publishTime = stringToInstant(element.findElement(By.cssSelector(".bh_content .ff-nn")).getText());

        return News.builder()
                .url(url)
                .imgUrl(imgUrl)
                .title(title)
                .publishTime(publishTime)
                .build();
    }

    public Instant stringToInstant(String time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        return LocalDate.parse(time, formatter)
                .atStartOfDay()
                .atZone(ZoneId.of("UTC"))
                .toInstant();
    }

    public ChromeDriver getDriver() {
        if (ObjectUtils.isEmpty(System.getProperty(CHROME_DRIVER_KEY))) {
            System.setProperty(CHROME_DRIVER_KEY, CHROME_DRIVER_PATH);
        }

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        return new ChromeDriver(options);
    }
}

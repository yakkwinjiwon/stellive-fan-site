package com.stellive.fansite.external;

import com.stellive.fansite.domain.News;
import com.stellive.fansite.exceptions.NewsRetrievalException;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static com.stellive.fansite.utils.WebScraperConst.*;

@Slf4j
@Component
public class NewsScraper {

    public List<News> getNews() {
        ChromeDriver driver = getDriver();

        try {
            driver.get(URL_OFFICIAL_NEWS);
            Thread.sleep(1000);
            return parseElement(driver);
        } catch (InterruptedException e) {
            throw new NewsRetrievalException(e);
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
        WebElement newsElement = element.findElement(By.xpath("./div"));

        String url = newsElement.findElement(By.xpath("./div[1]/a")).getAttribute("href");
        String imgUrl = newsElement.findElement(By.xpath("./div[1]/a/img")).getAttribute("src");
        String title = newsElement.findElement(By.xpath("./div[2]/div[3]/a/span")).getText();
        Instant publishTime = stringToInstant(newsElement.findElement(By.xpath("./div[2]/div[4]/p/span/span")).getText());

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

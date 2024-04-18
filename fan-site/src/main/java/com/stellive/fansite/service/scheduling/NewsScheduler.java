package com.stellive.fansite.service.scheduling;

import com.stellive.fansite.domain.News;
import com.stellive.fansite.scraper.NewsScraper;
import com.stellive.fansite.repository.News.NewsRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class NewsScheduler {

    private final NewsScraper newsScraper;

    private final NewsRepo newsRepo;

    public List<News> updateNotices(ChromeDriver driver,
                                    WebDriverWait wait) {
        log.info("update Notices");
        List<News> notices = newsScraper.scrapeNews(driver, wait);
        log.info("updated Notices={}", notices);
        return newsRepo.save(notices);
    }
}

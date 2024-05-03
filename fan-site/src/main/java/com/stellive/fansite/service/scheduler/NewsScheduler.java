package com.stellive.fansite.service.scheduler;

import com.stellive.fansite.domain.News;
import com.stellive.fansite.scraper.NewsScraper;
import com.stellive.fansite.repository.News.NewsRepo;
import com.stellive.fansite.utils.ScraperUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class NewsScheduler {

    private final NewsScraper newsScraper;

    private final NewsRepo newsRepo;

    public List<News> updateNews(ChromeDriver driver,
                                 WebDriverWait wait) {
        log.info("update News");

        List<News> scrapedNews = new ArrayList<>();
        ScraperUtils.executeWithHandling(() -> {
            scrapedNews.addAll(newsScraper.scrapeNews(driver, wait));
        });

        List<News> updatedNews = newsRepo.saveAll(scrapedNews);
        log.info("updated News={}", updatedNews);
        return updatedNews;
    }
}

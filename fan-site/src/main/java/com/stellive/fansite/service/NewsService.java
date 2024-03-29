package com.stellive.fansite.service;

import com.stellive.fansite.domain.News;
import com.stellive.fansite.external.NewsScraper;
import com.stellive.fansite.repository.News.NewsRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class NewsService {

    private final NewsScraper webScraper;

    private final NewsRepo noticeRepo;

    public List<News> updateNotices() {
        log.info("update Notices");
        List<News> notices = webScraper.getNews();
        log.info("updated Notices={}", notices);
        return noticeRepo.save(notices);
    }

    public List<News> findAllNotices() {
        return noticeRepo.findAll();
    }
}

package com.stellive.fansite.service.scheduling;

import com.stellive.fansite.domain.News;
import com.stellive.fansite.external.NewsScraper;
import com.stellive.fansite.repository.News.NewsRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class NewsService {

    private final NewsScraper newsScraper;

    private final NewsRepo noticeRepo;

    public List<News> updateNotices() {
        log.info("update Notices");
        List<News> notices = newsScraper.getNews();
        log.info("updated Notices={}", notices);
        return noticeRepo.save(notices);
    }

    public List<News> findAllNotices() {
        return noticeRepo.findAll();
    }
}

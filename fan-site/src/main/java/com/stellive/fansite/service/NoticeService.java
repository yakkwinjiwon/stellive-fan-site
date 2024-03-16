package com.stellive.fansite.service;

import com.stellive.fansite.domain.Notice;
import com.stellive.fansite.external.WebScraper;
import com.stellive.fansite.repository.Notice.NoticeRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepo noticeRepo;

    private final WebScraper webScraper;

    public List<Notice> updateNotices() {
        List<Notice> notices = webScraper.getNotices();
        log.info("updateNotices={}", notices);
        return noticeRepo.save(notices);
    }

    public List<Notice> findAllNotices() {
        return noticeRepo.findAll();
    }
}

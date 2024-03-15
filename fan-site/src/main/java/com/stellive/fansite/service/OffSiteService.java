package com.stellive.fansite.service;

import com.stellive.fansite.domain.Notice;
import com.stellive.fansite.external.WebScraper;
import com.stellive.fansite.repository.OffSiteRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class OffSiteService {

    private final OffSiteRepo repo;

    private final WebScraper webScraper;

    public void updateAll() {
        List<Notice> updatedNotices = updateNotices();
    }

    public List<Notice> updateNotices() {
        List<Notice> notices = webScraper.getNotices();
        return repo.saveNotices(notices);
    }

    public List<Notice> findAllNotices() {
        return repo.findAllNotices();
    }
}

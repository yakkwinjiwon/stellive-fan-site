package com.stellive.fansite.repository;

import com.stellive.fansite.domain.Notice;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Slf4j
@RequiredArgsConstructor
public class OffSiteDataRepo implements OffSiteRepo {

    private final OffSiteNoticeDataRepo noticeRepo;

    @Override
    public List<Notice> saveNotices(List<Notice> notices) {
        return noticeRepo.saveAll(notices);
    }

    @Override
    public List<Notice> findAllNotices() {
        return noticeRepo.findAll();
    }
}

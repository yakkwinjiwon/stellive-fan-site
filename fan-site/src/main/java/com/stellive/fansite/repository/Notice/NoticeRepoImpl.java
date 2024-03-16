package com.stellive.fansite.repository.Notice;

import com.stellive.fansite.domain.Notice;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Slf4j
@RequiredArgsConstructor
public class NoticeRepoImpl implements NoticeRepo {

    private final NoticeDataRepo noticeDataRepo;

    @Override
    public List<Notice> save(List<Notice> notices) {
        return noticeDataRepo.saveAll(notices);
    }

    @Override
    public List<Notice> findAll() {
        return noticeDataRepo.findAll();
    }
}

package com.stellive.fansite.repository.News;

import com.stellive.fansite.domain.News;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Slf4j
@RequiredArgsConstructor
public class NewsRepoImpl implements NewsRepo {

    private final NewsDataRepo noticeDataRepo;

    @Override
    public List<News> save(List<News> notices) {
        return noticeDataRepo.saveAll(notices);
    }

    @Override
    public List<News> findAll() {
        return noticeDataRepo.findAll();
    }
}

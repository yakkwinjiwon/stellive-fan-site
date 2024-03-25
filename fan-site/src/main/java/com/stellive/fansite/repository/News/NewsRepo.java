package com.stellive.fansite.repository.News;

import com.stellive.fansite.domain.News;

import java.util.List;

public interface NewsRepo {

    List<News> save(List<News> notices);

    List<News> findAll();
}

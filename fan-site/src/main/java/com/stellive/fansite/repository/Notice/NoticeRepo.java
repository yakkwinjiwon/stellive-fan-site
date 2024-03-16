package com.stellive.fansite.repository.Notice;

import com.stellive.fansite.domain.Notice;

import java.util.List;

public interface NoticeRepo {

    List<Notice> save(List<Notice> notices);

    List<Notice> findAll();
}

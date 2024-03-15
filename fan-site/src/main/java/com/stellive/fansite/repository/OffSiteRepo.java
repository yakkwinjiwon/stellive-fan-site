package com.stellive.fansite.repository;

import com.stellive.fansite.domain.Notice;

import java.util.List;

public interface OffSiteRepo {

    List<Notice> saveNotices(List<Notice> notices);

    List<Notice> findAllNotices();
}

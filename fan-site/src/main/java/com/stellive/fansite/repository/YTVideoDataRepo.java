package com.stellive.fansite.repository;

import com.stellive.fansite.domain.YTVideo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface YTVideoDataRepo extends JpaRepository<YTVideo, Long> {

    List<YTVideo> findAllByUserId(Long id);
}

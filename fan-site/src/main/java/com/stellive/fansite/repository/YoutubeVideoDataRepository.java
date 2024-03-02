package com.stellive.fansite.repository;

import com.stellive.fansite.domain.Video;
import org.springframework.data.jpa.repository.JpaRepository;

public interface YoutubeVideoDataRepository extends JpaRepository<Video, Long> {
}

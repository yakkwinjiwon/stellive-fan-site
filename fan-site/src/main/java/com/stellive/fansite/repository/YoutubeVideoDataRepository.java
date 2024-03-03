package com.stellive.fansite.repository;

import com.stellive.fansite.domain.Video;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface YoutubeVideoDataRepository extends JpaRepository<Video, Long> {

    List<Video> findByChannelId(Long channelId);
}

package com.stellive.fansite.repository.Video;

import com.stellive.fansite.domain.VideoType;
import com.stellive.fansite.domain.Video;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VideoDataRepo extends JpaRepository<Video, Long> {

    List<Video> findByChannelId(Long channelId);
    List<Video> findByChannelIdAndVideoType(Long channelId, VideoType videoType);
}

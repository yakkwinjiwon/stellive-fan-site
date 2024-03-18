package com.stellive.fansite.repository.Video;

import com.stellive.fansite.domain.VideoType;
import com.stellive.fansite.domain.Video;

import java.util.List;

public interface VideoRepo {

    List<Video> save(List<Video> videos);
    List<Video> findByChannelId(Long channelId);
    List<Video> findByChannelIdAndVideoType(Long channelId, VideoType videoType);
}

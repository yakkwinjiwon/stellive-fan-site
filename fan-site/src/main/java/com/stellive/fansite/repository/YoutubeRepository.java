package com.stellive.fansite.repository;

import com.stellive.fansite.domain.Channel;
import com.stellive.fansite.domain.Video;

import java.util.List;
import java.util.Optional;

public interface YoutubeRepository {

    Channel saveChannel(Channel channel);
    Optional<Channel> findChannelById(Long id);
    List<Video> saveChannelVideos(List<Video> videos);
    List<Video> saveAllVideos(List<Video> videos);


}

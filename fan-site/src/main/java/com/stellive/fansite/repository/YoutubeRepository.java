package com.stellive.fansite.repository;

import com.stellive.fansite.domain.Channel;
import com.stellive.fansite.domain.Video;

import java.util.List;
import java.util.Optional;

public interface YoutubeRepository {

    Channel saveChannel(Channel channel);
    Optional<Channel> findChannelById(Long id);
    Optional<Channel> findChannelByExternalId(String externalId);
    List<Video> saveVideos(List<Video> videos);

    List<Video> findVideosByChannelId(Long id);
    List<Video> saveAllVideos(List<Video> videos);


}

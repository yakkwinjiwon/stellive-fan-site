package com.stellive.fansite.repository;

import com.stellive.fansite.domain.Video;

import java.util.List;

public interface YoutubeApiRepository {

    List<Video> saveChannelVideos(List<Video> videos);
    List<Video> saveAllVideos(List<Video> videos);

}

package com.stellive.fansite.repository;

import com.stellive.fansite.domain.Video;

import java.util.List;

public interface YoutubeRepository {

    List<Video> saveAllVideos(List<Video> videos);

}

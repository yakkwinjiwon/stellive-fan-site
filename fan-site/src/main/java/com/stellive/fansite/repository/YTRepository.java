package com.stellive.fansite.repository;

import com.stellive.fansite.domain.YTUser;
import com.stellive.fansite.domain.YTVideo;

import java.util.List;
import java.util.Optional;

public interface YTRepository {

    YTUser saveYTUser(YTUser channel);
    Optional<YTUser> findYTUserById(Long id);
    List<YTUser> findAllYTUsers();

    List<YTVideo> saveYTVideos(List<YTVideo> videos);
    List<YTVideo> findYTVideosByYTUserId(Long id);

}

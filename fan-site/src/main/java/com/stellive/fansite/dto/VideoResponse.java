package com.stellive.fansite.dto;

import com.stellive.fansite.domain.Video;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class VideoResponse {

    List<Video> videos;
    String nextPageToken;
}

package com.stellive.fansite.dto.video;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class VideoList {

    private List<VideoItem> items;
}

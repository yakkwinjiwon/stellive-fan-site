package com.stellive.fansite.dto.video;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VideoSnippet {

    private String publishedAt;
    private String channelId;
    private String title;
    private String liveBroadcastContent;
    private VideoThumbnails thumbnails;
}

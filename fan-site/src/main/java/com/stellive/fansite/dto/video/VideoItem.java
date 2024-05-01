package com.stellive.fansite.dto.video;

import com.stellive.fansite.domain.Video;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VideoItem {

    private VideoContentDetails contentDetails;
    private VideoLiveStreamingDetails liveStreamingDetails;
    private VideoSnippet snippet;
    private VideoStatistics statistics;
    private String id;
}

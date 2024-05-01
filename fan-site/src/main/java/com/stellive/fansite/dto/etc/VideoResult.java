package com.stellive.fansite.dto.etc;

import com.stellive.fansite.domain.Video;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class VideoResult {

    List<String> videoIds;
    String nextPageToken;
}

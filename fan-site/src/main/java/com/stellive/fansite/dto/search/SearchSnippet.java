package com.stellive.fansite.dto.search;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SearchSnippet {

    private String channelId;
    private String title;
    private SearchThumbnails thumbnails;
    private String publishTime;
}

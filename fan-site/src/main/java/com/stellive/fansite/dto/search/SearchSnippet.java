package com.stellive.fansite.dto.search;

import lombok.Getter;

@Getter
public class SearchSnippet {

    private String channelId;
    private String title;
    private SearchThumbnails thumbnails;
    private String publishTime;
}

package com.stellive.fansite.dto.playlistitems;

import lombok.Getter;

@Getter
public class PlaylistItemsSnippet {

    private String publishedAt;
    private String channelId;
    private String title;
    private PlaylistItemsThumbnails thumbnails;
    private PlaylistItemsResourceId resourceId;

}

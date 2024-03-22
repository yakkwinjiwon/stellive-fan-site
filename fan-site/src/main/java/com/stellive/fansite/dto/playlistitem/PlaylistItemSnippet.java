package com.stellive.fansite.dto.playlistitem;

import lombok.Getter;

@Getter
public class PlaylistItemSnippet {

    private String videoOwnerChannelId;
    private String publishedAt;
    private String title;
    private PlaylistItemThumbnails thumbnails;
    private PlaylistItemResourceId resourceId;

}

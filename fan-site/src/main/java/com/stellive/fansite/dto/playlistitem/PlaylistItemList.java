package com.stellive.fansite.dto.playlistitem;

import lombok.Getter;

import java.util.List;

@Getter
public class PlaylistItemList {

    private String nextPageToken;
    private List<PlaylistItemItem> items;
}

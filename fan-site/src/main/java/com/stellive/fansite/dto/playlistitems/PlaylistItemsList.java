package com.stellive.fansite.dto.playlistitems;

import lombok.Getter;

import java.util.List;

@Getter
public class PlaylistItemsList {

    private String nextPageToken;
    private List<PlaylistItemsItem> items;
}

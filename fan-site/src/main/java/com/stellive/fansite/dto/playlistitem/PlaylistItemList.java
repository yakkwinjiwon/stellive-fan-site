package com.stellive.fansite.dto.playlistitem;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class PlaylistItemList {

    private String nextPageToken;
    private List<PlaylistItemItem> items;
}

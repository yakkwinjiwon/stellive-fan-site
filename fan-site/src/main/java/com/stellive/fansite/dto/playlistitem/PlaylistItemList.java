package com.stellive.fansite.dto.playlistitem;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PlaylistItemList {

    private String nextPageToken;
    private List<PlaylistItemItem> items;

}

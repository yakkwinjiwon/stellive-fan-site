package com.stellive.fansite.dto.playlistitem;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PlaylistItemSnippet {

    private PlaylistItemThumbnails thumbnails;
    private PlaylistItemResourceId resourceId;

}

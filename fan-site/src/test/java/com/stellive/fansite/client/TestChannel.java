package com.stellive.fansite.client;

import com.stellive.fansite.domain.YoutubeChannel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public enum TestChannel implements YoutubeChannel {

    OK(1L, "UC7_uO3Vsk323nA3LeQX0_sw",
            List.of("PUsF-WIxstzZmzk70V2aj1e6NawmmquvdR",
                    "UL7_uO3Vsk323nA3LeQX0_sw")),

    INVALID_CHANNEL_ID(2L, "Invalid Channel Id",
            List.of("PUsF-WIxstzZmzk70V2aj1e6NawmmquvdR",
                    "UL7_uO3Vsk323nA3LeQX0_sw")),

    INVALID_PLAYLIST_IDS(3L, "UC7_uO3Vsk323nA3LeQX0_sw",
            List.of("Invalid Playlist Id"));

    private final Long id;
    private final String channelId;
    private final List<String> musicPlaylistIds;
}

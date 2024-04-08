package com.stellive.fansite.client;

import com.stellive.fansite.domain.YoutubeChannel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

import static com.stellive.fansite.utils.TestConst.*;

@Getter
@AllArgsConstructor
public enum TestChannel implements YoutubeChannel {

    VALID(1L, CHANNEL_ID_VALID,
            List.of(PLAYLIST_ID_VALID,
                    PLAYLIST_ID_VALID)),

    INVALID_BY_CHANNEL_ID(2L, CHANNEL_ID_INVALID,
            List.of(PLAYLIST_ID_VALID,
                    PLAYLIST_ID_VALID)),


    INVALID_BY_PLAYLIST_IDS(3L, CHANNEL_ID_VALID,
            List.of(PLAYLIST_ID_INVALID,
                    PLAYLIST_ID_INVALID));

    private final Long id;
    private final String channelId;
    private final List<String> musicPlaylistIds;

    @Override
    public Boolean isReplay() {
        return id % 10 == 2;
    }
}

package com.stellive.fansite.client;

import com.stellive.fansite.domain.YoutubeChannel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

import static com.stellive.fansite.utils.TestConst.*;

@Getter
@AllArgsConstructor
public enum TestChannel implements YoutubeChannel {

    VALID(1L, CHANNEL_ID_VALID),

    INVALID_BY_CHANNEL_ID(2L, CHANNEL_ID_INVALID),

    INVALID_BY_PLAYLIST_IDS(3L, CHANNEL_ID_VALID);

    private final Long id;
    private final String channelId;

    @Override
    public Boolean isReplay() {
        return id % 10 == 2;
    }
}

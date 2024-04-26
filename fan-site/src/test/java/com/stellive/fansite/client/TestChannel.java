package com.stellive.fansite.client;

import com.stellive.fansite.domain.YoutubeChannel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

import static com.stellive.fansite.utils.TestConst.*;

@Getter
@AllArgsConstructor
public enum TestChannel implements YoutubeChannel {

    VALID(1L, CHANNEL_ID);

    private final Long id;
    private final String channelId;

    @Override
    public Boolean isReplay() {
        return id % 10 == 2;
    }
}

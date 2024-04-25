package com.stellive.fansite.api.Youtube;

import com.stellive.fansite.client.TestChannel;
import com.stellive.fansite.domain.Channel;
import com.stellive.fansite.domain.YoutubeChannel;
import com.stellive.fansite.dto.channel.ChannelList;
import com.stellive.fansite.repository.Video.VideoRepo;
import com.stellive.fansite.utils.TestConst;
import com.stellive.fansite.utils.TestUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static com.stellive.fansite.utils.TestConst.*;
import static org.junit.jupiter.api.Assertions.*;

class ChannelFetcherTest {

    ChannelFetcher channelFetcher;

    @Mock
    ChannelConnector channelConnector;

    @Mock
    VideoRepo videoRepo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        channelFetcher = new ChannelFetcher(channelConnector, videoRepo);
    }

    @Test
    @DisplayName("ChannelList를 받아 Channel반환")
    void testFetchChannel() {
        // given
        YoutubeChannel youtubeChannel = TestChannel.VALID;
        ChannelList channelList = TestUtils.getChannelList();
        Mockito.when(channelConnector.callChannel(youtubeChannel)).thenReturn(channelList);

        Channel channel = Channel.builder()
                .id(youtubeChannel.getId())
                .externalId(youtubeChannel.getChannelId())
                .videos(videoRepo.findByChannelId(youtubeChannel.getId()))
                .handle(CHANNEL_CUSTOM_URL)
                .thumbnailUrl(CHANNEL_THUMBNAIL_URL)
                .bannerUrl(CHANNEL_BANNER_EXTERNAL_URL)
                .build();

        // when
        Channel fetchedChannel = channelFetcher.fetchChannel(youtubeChannel);

        // then
        Assertions.assertThat(fetchedChannel).isEqualTo(channel);
    }
}
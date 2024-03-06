package com.stellive.fansite.service;

import com.stellive.fansite.domain.Channel;
import com.stellive.fansite.domain.ChannelId;
import com.stellive.fansite.domain.Video;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static com.stellive.fansite.domain.ChannelId.*;
import static org.assertj.core.api.Assertions.*;

@Slf4j
@Transactional
@SpringBootTest
class YoutubeApiServiceTest {

    @Autowired
    YoutubeApiService youtubeApiService;

    @Test
    void testDataInitTest() {
        Arrays.stream(ChannelId.values())
                .forEach(stella -> {
                    Channel findChannel = youtubeApiService.findChannelById(stella.getId());
                    assertThat(findChannel.getExternalId()).isEqualTo(stella.getYoutubeId());
                });
    }

    @Test
    void updateChannelTest() {
        Channel channel = youtubeApiService.updateChannel(MASHIRO);

        assertThat(channel.getId()).isEqualTo(MASHIRO.getId());
    }

    @Test
    void findChannelByIdTest() {
        Channel channel = youtubeApiService.findChannelById(MASHIRO.getId());

        assertThat(channel.getId()).isEqualTo(MASHIRO.getId());
    }

    @Test
    void updateVideosTest() {
        List<Video> videos = youtubeApiService.updateVideos(MASHIRO);

        assertThat(videos.getFirst().getChannel().getId()).isEqualTo(MASHIRO.getId());
    }

    @Test
    void findVideosByIdTest() {
        List<Video> videos = youtubeApiService.findVideosByChannelId(MASHIRO.getId());

        assertThat(videos.getFirst().getChannel().getId()).isEqualTo(MASHIRO.getId());
    }

    @Test
    void updateAllChannelsTest() {

    }
}
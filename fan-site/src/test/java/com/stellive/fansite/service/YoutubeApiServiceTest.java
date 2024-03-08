package com.stellive.fansite.service;

import com.stellive.fansite.domain.YTUser;
import com.stellive.fansite.domain.Stella;
import com.stellive.fansite.domain.YTVideo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static com.stellive.fansite.domain.Stella.*;
import static org.assertj.core.api.Assertions.*;

@Slf4j
@Transactional
@SpringBootTest
class YoutubeApiServiceTest {

    @Autowired
    YTApiService youtubeApiService;

    @Test
    void testDataInitTest() {
        Arrays.stream(Stella.values())
                .forEach(stella -> {
                    YTUser findChannel = youtubeApiService.findYTUserById(stella.getId());
                    assertThat(findChannel.getExternalId()).isEqualTo(stella.getYoutubeId());
                });
    }

    @Test
    void updateChannelTest() {
        YTUser channel = youtubeApiService.updateYTUser(MASHIRO);

        assertThat(channel.getId()).isEqualTo(MASHIRO.getId());
    }

    @Test
    void findChannelByIdTest() {
        YTUser channel = youtubeApiService.findYTUserById(MASHIRO.getId());

        assertThat(channel.getId()).isEqualTo(MASHIRO.getId());
    }

    @Test
    void updateVideosTest() {
        List<YTVideo> videos = youtubeApiService.updateYTVideos(MASHIRO);

        assertThat(videos.getFirst().getUser().getId()).isEqualTo(MASHIRO.getId());
    }

    @Test
    void findVideosByIdTest() {
        List<YTVideo> videos = youtubeApiService.findYTVideosByYTUserId(MASHIRO.getId());

        assertThat(videos.getFirst().getUser().getId()).isEqualTo(MASHIRO.getId());
    }

    @Test
    void updateAllChannelsTest() {

        
    }
}
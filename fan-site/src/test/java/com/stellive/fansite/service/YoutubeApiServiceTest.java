package com.stellive.fansite.service;

import com.stellive.fansite.domain.Channel;
import com.stellive.fansite.domain.ChannelId;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

import static com.stellive.fansite.domain.ChannelId.*;
import static com.stellive.fansite.utils.YoutubeApiConst.*;
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
                    assertThat(findChannel.getExternalId()).isEqualTo(stella.getExternalId());
                });
    }

    @Test
    void updateChannelTest() {
        Channel official = youtubeApiService.updateChannel(MASHIRO);

        assertThat(official.getExternalId()).isEqualTo(MASHIRO.getExternalId());
    }

    @Test
    void findChannelByIdTest() {
        Channel official = youtubeApiService.findChannelById(7L);

        assertThat(official.getExternalId()).isEqualTo(MASHIRO.getExternalId());
    }

    @Test
    void updateAllChannelsTest() {

    }
}
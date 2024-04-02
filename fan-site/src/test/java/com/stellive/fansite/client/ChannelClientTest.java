package com.stellive.fansite.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stellive.fansite.domain.Channel;
import com.stellive.fansite.exceptions.EmptyItemException;
import com.stellive.fansite.repository.Video.VideoRepo;
import com.stellive.fansite.repository.Video.VideoRepoImpl;
import com.stellive.fansite.utils.ApiUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
class ChannelClientTest {

    @Mock
    RestTemplate restTemplate;
    @Mock
    ApiUtils apiUtils;
    @Mock
    VideoRepo videoRepo;
    @InjectMocks
    ChannelClient channelClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        restTemplate = new RestTemplate();
        apiUtils = new ApiUtils(new ObjectMapper());
        videoRepo = new VideoRepoImpl();
    }

    @Test
    @DisplayName("정상적인 채널 정보 가져오기")
    void validChannel() {
        //Arrange
        Channel okChannel = channelClient.getChannel(TestChannel.OK);
        log.info("Channel={}", okChannel);
        //Act
        //Assert
        assertThat(okChannel.getExternalId()).isEqualTo(TestChannel.OK.getChannelId());
    }

    @Test
    @DisplayName("잘못된 ID로 호출시 재시도")
    void InvalidChannelId() {
        //Arrange
        //Act
        //Assert
        assertThrows(EmptyItemException.class,
                () -> channelClient.getChannel(TestChannel.INVALID_CHANNEL_ID));
    }
}
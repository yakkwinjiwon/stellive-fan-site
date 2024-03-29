package com.stellive.fansite.client;

import com.stellive.fansite.domain.Channel;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestClientException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
@SpringBootTest
class ChannelClientTest {

    @Autowired
    ChannelClient channelClient;

    @Test
    @DisplayName("정상적인 채널 정보 가져오기")
    void okChannel() {
        //Arrange
        Channel okChannel = channelClient.getChannel(TestChannel.OK);
        log.info("Channel={}", okChannel);
        //Act
        //Assert
        assertThat(okChannel.getExternalId()).isEqualTo(TestChannel.OK.getChannelId());
    }

    @Test
    @DisplayName("RestClientException 발생 시 재시도")
    void retryRestClientException() {
        //Arrange
        //Act
        //Assert
        assertThrows(RestClientException.class,
                () -> channelClient.getChannel(TestChannel.INVALID_CHANNEL_ID));
    }
}
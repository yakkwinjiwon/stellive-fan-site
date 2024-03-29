package com.stellive.fansite.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stellive.fansite.dto.channel.ChannelList;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.stellive.fansite.utils.TestConst.CHANNEL_ID;
import static com.stellive.fansite.utils.TestConst.CHANNEL_JSON;

class ApiUtilsTest {

    static ObjectMapper objectMapper;

    @BeforeAll
    static void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Test
    @DisplayName("API 응답 파싱 성공")
    void parseResponse() throws JsonProcessingException {
        //Arrange
        ChannelList list = objectMapper.readValue(CHANNEL_JSON, ChannelList.class);
        //Act
        //Assert
        Assertions.assertThat(list.getItems().getFirst().getId()).isEqualTo(CHANNEL_ID);
    }
}
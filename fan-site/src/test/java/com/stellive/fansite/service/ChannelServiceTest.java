package com.stellive.fansite.service;

import com.stellive.fansite.api.ChannelFetcher;
import com.stellive.fansite.domain.Channel;
import com.stellive.fansite.domain.News;
import com.stellive.fansite.domain.StellaChannel;
import com.stellive.fansite.external.NewsScraper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@Slf4j
@SpringBootTest
class ChannelServiceTest {

    @Autowired
    ChannelFetcher channelFetcher;

    @Autowired
    NewsScraper newsScraper;

    @Test
    void updateChannels() {
        Channel channel = channelFetcher.fetchChannel(StellaChannel.HINA);
        log.info("channel={}", channel);

        List<News> newses = newsScraper.getNews();
        newses.forEach(news -> log.info("news={}", news));

    }

    @Test
    void updateChannel() {
    }
}
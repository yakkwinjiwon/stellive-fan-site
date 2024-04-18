package com.stellive.fansite.service;

import com.stellive.fansite.api.Youtube.PlaylistItemFetcher;
import com.stellive.fansite.api.Youtube.VideoFetcher;
import com.stellive.fansite.scraper.NewsScraper;
import com.stellive.fansite.service.scheduling.ChannelScheduler;
import com.stellive.fansite.service.scheduling.VideoScheduler;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static com.stellive.fansite.utils.ApiConst.*;

@Transactional
@Slf4j
@SpringBootTest
class ChannelServiceTest {

    @Autowired
    ChannelScheduler channelScheduler;

    @Autowired
    PlaylistItemFetcher playlistItemFetcher;

    @Autowired
    VideoFetcher videoFetcher;

    @Autowired
    NewsScraper newsScraper;

    @Autowired
    VideoScheduler videoScheduler;

    @Autowired
    TestClass testClass;

    @Test
    void updateChannels() {
        channelScheduler.updateChannels();

        videoScheduler.updateVideos(MAX_RESULTS_VIDEO);
    }

    @Test
    void updateChannel() {
         testClass.callApi();
    }


}
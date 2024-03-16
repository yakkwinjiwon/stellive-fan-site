package com.stellive.fansite;

import com.stellive.fansite.service.ChannelService;
import com.stellive.fansite.service.NoticeService;
import com.stellive.fansite.service.VideoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
@Slf4j
public class FanSiteApplication {

    public static void main(String[] args) {
        SpringApplication.run(FanSiteApplication.class, args);
    }

    @Bean
    @Profile("local")
    public TestDataInit localDataInit(ChannelService channelService,
                                      VideoService videoService,
                                      NoticeService noticeService) {
        return new TestDataInit(channelService,
                videoService,
                noticeService);
    }
}

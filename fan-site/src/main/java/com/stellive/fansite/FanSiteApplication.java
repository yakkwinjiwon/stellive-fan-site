package com.stellive.fansite;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
//@EnableScheduling
@EnableRetry
public class FanSiteApplication {

    public static void main(String[] args) {
        SpringApplication.run(FanSiteApplication.class, args);
    }
/*
    @Profile("local")
    @Bean
    public TestDataInit localDataInit(ChannelService channelService,
                                      VideoService videoService,
                                      NoticeService noticeService) {
        return new TestDataInit(channelService,
                videoService,
                noticeService);
    }*/
}

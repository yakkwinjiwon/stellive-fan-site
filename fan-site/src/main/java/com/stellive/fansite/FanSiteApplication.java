package com.stellive.fansite;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FanSiteApplication {

    public static void main(String[] args) {
        SpringApplication.run(FanSiteApplication.class, args);
    }
//    @Bean
//    @Profile("local")
//    public TestDataInit localDataInit(ChannelService channelService,
//                                      VideoService videoService,
//                                      NoticeService noticeService) {
//        return new TestDataInit(channelService,
//                videoService,
//                noticeService);
//    }
}

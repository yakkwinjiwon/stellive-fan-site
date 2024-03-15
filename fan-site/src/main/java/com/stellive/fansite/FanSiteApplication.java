package com.stellive.fansite;

import com.stellive.fansite.service.YTApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

@Profile({"local", "test"})
@SpringBootApplication
@Slf4j
public class FanSiteApplication {

    public static void main(String[] args) {
        SpringApplication.run(FanSiteApplication.class, args);
    }

    @Bean
    @Profile("local")
    public TestDataInit localDataInit(YTApiService youtubeApiService) {
        return new TestDataInit(youtubeApiService);
    }

    @Bean
    @Profile("test")
    public TestDataInit testDataInit(YTApiService youtubeApiService) {
        return new TestDataInit(youtubeApiService);
    }
}

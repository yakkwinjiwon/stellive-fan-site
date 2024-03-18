package com.stellive.fansite.scheduling;

import com.stellive.fansite.client.ChannelClient;
import com.stellive.fansite.client.VideoClient;
import com.stellive.fansite.domain.Channel;
import com.stellive.fansite.domain.YoutubeChannel;
import com.stellive.fansite.repository.Channel.ChannelRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
@Slf4j
public class YoutubeScheduling {

    private final ChannelClient channelClient;
    private final VideoClient videoClient;

    private final ChannelRepo channelRepo;

//    @Scheduled(fixedRate = 10000)

    /**
     *
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void updateAll() {
        log.info("updateAll");
        log.info("updateAllChannels");
        updateAllChannels();
        log.info("updateAllVideos");
        updateAllVideos();
    }
    public void updateAllChannels() {
        Arrays.stream(YoutubeChannel.values())
                .forEach(youtubeChannel -> {
                    Channel channel = channelClient.getChannel(youtubeChannel);
                    channelRepo.save(channel);
                    log.info("updated channel={}", channel);
                });
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void updateAllVideos() {
        log.info("updateAllVideos");

    }
}

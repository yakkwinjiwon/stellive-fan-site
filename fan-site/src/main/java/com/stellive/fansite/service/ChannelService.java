package com.stellive.fansite.service;

import com.stellive.fansite.client.ChannelClient;
import com.stellive.fansite.domain.YoutubeChannel;
import com.stellive.fansite.domain.Channel;
import com.stellive.fansite.repository.Channel.ChannelRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChannelService {

    private final ChannelClient channelClient;
    private final ChannelRepo channelRepo;

    public List<Channel> updateAll() {

        log.info("Update all Channels");
        List<Channel> channels = new ArrayList<>();

        Arrays.stream(YoutubeChannel.values())
                .forEach(stella -> {
                    Channel channel = update(stella);
                    channels.add(channel);
                });
        return channels;
    }

    public Channel update(YoutubeChannel stella) {

        Channel channel = channelClient.getChannel(stella);
        log.info("Updated Channel={}", channel);
        return channelRepo.save(channel);
    }

    public Channel findById(Long id) {
        return channelRepo.findById(id).orElseGet(Channel::new);
    }

    public List<Channel> findAll() {
        return channelRepo.findAll();
    }
}

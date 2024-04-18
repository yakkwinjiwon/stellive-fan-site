package com.stellive.fansite.service.scheduling;

import com.stellive.fansite.api.Youtube.ChannelFetcher;
import com.stellive.fansite.domain.Channel;
import com.stellive.fansite.repository.Channel.ChannelRepo;
import com.stellive.fansite.utils.ApiUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChannelScheduler {

    private final ChannelFetcher channelFetcher;

    private final ChannelRepo channelRepo;

    public List<Channel> updateChannels() {
        log.info("Update all Channels");
        List<Channel> channels = new ArrayList<>();

        ApiUtils.executeForEachChannel(youtubeChannel -> {
            Channel fetchedChannel = channelFetcher.fetchChannel(youtubeChannel);
            Channel updatedChannel = updateChannel(fetchedChannel);

            log.info("Updated Channel={}", updatedChannel);
            channels.add(updatedChannel);
        });

        return channels;
    }

    public Channel updateChannel(Channel channel) {
        return channelRepo.save(channel);
    }

}

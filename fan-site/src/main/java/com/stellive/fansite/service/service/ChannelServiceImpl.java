package com.stellive.fansite.service.service;

import com.stellive.fansite.domain.Channel;
import com.stellive.fansite.repository.Channel.ChannelRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChannelServiceImpl implements ChannelService{

    private final ChannelRepo channelRepo;

    @Override
    public List<Channel> findAll() {
        return channelRepo.findAll();
    }
}

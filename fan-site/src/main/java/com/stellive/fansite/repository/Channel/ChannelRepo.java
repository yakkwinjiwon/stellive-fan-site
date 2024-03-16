package com.stellive.fansite.repository.Channel;

import com.stellive.fansite.domain.Channel;

import java.util.List;
import java.util.Optional;

public interface ChannelRepo {

    Channel save(Channel channel);
    Optional<Channel> findById(Long id);
    List<Channel> findAll();

}

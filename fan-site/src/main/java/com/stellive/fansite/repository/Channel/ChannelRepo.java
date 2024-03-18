package com.stellive.fansite.repository.Channel;

import com.stellive.fansite.domain.Channel;

import java.util.List;
import java.util.Optional;

public interface ChannelRepo {

    Channel save(Channel channel);
    Optional<Channel> findById(Long id);
<<<<<<< HEAD
    Optional<Channel> findByExternalId(String externalId);
=======
>>>>>>> fab86610301f7b13c8269bf0d6aec89c26b20472
    List<Channel> findAll();

}

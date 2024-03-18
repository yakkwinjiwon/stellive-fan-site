package com.stellive.fansite.repository.Channel;

import com.stellive.fansite.domain.Channel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ChannelRepoImpl implements ChannelRepo {

    private final ChannelDataRepo channelDataRepo;

    @Override
    public Channel save(Channel channel) {
        return channelDataRepo.save(channel);
    }

    @Override
    public Optional<Channel> findById(Long id) {
        return channelDataRepo.findById(id);
    }

    @Override
<<<<<<< HEAD
    public Optional<Channel> findByExternalId(String externalId) {
        return channelDataRepo.findByExternalId(externalId);
    }

    @Override
=======
>>>>>>> fab86610301f7b13c8269bf0d6aec89c26b20472
    public List<Channel> findAll() {
        return channelDataRepo.findAll();
    }

}

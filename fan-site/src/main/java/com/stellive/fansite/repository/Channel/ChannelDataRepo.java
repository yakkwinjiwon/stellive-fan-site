package com.stellive.fansite.repository.Channel;

import com.stellive.fansite.domain.Channel;
import org.springframework.data.jpa.repository.JpaRepository;

<<<<<<< HEAD
import java.util.Optional;

public interface ChannelDataRepo extends JpaRepository<Channel, Long> {

    Optional<Channel> findByExternalId(String externalId);
=======
public interface ChannelDataRepo extends JpaRepository<Channel, Long> {

>>>>>>> fab86610301f7b13c8269bf0d6aec89c26b20472
}

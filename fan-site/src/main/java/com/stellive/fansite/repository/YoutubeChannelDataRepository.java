package com.stellive.fansite.repository;

import com.stellive.fansite.domain.Channel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface YoutubeChannelDataRepository extends JpaRepository<Channel, Long> {

    Optional<Channel> findByExternalId(String externalId);
}

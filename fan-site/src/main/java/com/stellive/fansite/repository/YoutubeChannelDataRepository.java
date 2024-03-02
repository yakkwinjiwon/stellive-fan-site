package com.stellive.fansite.repository;

import com.stellive.fansite.domain.Channel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface YoutubeChannelDataRepository extends JpaRepository<Channel, Long> {
}

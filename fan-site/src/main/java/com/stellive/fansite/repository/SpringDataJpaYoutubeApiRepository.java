package com.stellive.fansite.repository;

import com.stellive.fansite.domain.Video;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataJpaYoutubeApiRepository extends JpaRepository<Video, Long> {


}

package com.stellive.fansite.service.service;

import com.stellive.fansite.domain.Video;
import com.stellive.fansite.repository.Video.VideoRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class VideoServiceImpl implements VideoService{

    private final VideoRepo videoRepo;

    @Override
    public List<Video> findAll() {
        return videoRepo.findAll();
    }
}

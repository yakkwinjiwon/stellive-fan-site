package com.stellive.fansite.controller;

import com.stellive.fansite.domain.Channel;
import com.stellive.fansite.service.scheduler.ChannelScheduler;
import com.stellive.fansite.service.scheduler.VideoScheduler;
import com.stellive.fansite.service.service.ChannelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:8081")
@RequiredArgsConstructor
@RequestMapping("")
@Slf4j
public class HomeController {

    private final ChannelService channelService;

    @GetMapping("/")
    public String home() {
        log.info("home");


        return "ok";
    }

    @GetMapping("/channels")
    public List<Channel> channels() {
        log.info("channels");

        return channelService.findAll();
    }

}

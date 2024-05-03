package com.stellive.fansite.controller;

import com.stellive.fansite.service.scheduler.ChannelScheduler;
import com.stellive.fansite.service.scheduler.VideoScheduler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/rest")
public class HomeController {

    private final ChannelScheduler channelService;
    private final VideoScheduler videoService;

    @GetMapping("")
    public String home() {
        log.info("home");

        return "ok";
    }
}

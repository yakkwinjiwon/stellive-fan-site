package com.stellive.fansite.controller;

import com.stellive.fansite.client.PlaylistItemsClient;
import com.stellive.fansite.service.ChannelService;
import com.stellive.fansite.service.NoticeService;
import com.stellive.fansite.service.VideoService;
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

    private final ChannelService channelService;
    private final VideoService videoService;
    private final NoticeService noticeService;
    private final PlaylistItemsClient client;

    @GetMapping("")
    public String home() {
        log.info("home");

        client.getVieosFromPlaylist("PLzdLDJsHzz2OrqnrRIVuYJOdC6uID1cjq", false);

        return "ok";
    }
}

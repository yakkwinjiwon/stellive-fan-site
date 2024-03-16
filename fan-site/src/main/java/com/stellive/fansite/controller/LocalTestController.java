package com.stellive.fansite.controller;

import com.stellive.fansite.service.ChannelService;
import com.stellive.fansite.service.NoticeService;
import com.stellive.fansite.service.VideoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("")
@RequiredArgsConstructor
public class LocalTestController {

    private final ChannelService channelService;
    private final VideoService videoService;
    private final NoticeService noticeService;

    @GetMapping("")
    public String home(Model model) {
        return "home";
    }


}

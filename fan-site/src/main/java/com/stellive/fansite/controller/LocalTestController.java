package com.stellive.fansite.controller;

import com.stellive.fansite.domain.Channel;
import com.stellive.fansite.service.YoutubeApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("")
@RequiredArgsConstructor
public class LocalTestController {

    private final YoutubeApiService youtubeApiService;

    @GetMapping("")
    public String home(Model model) {
        List<Channel> youtubeChannels = youtubeApiService.findAllChannels();
        model.addAttribute("youtubeChannels", youtubeChannels);
        return "home";
    }


}

package com.stellive.fansite.controller;

import com.stellive.fansite.domain.YTUser;
import com.stellive.fansite.service.YTApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("")
@RequiredArgsConstructor
public class LocalTestController {

    private final YTApiService youtubeApiService;

    @GetMapping("")
    public String home(@NotNull Model model) {
        List<YTUser> youtubeChannels = youtubeApiService.findAllYTUsers();
        model.addAttribute("youtubeChannels", youtubeChannels);
        return "home";
    }


}

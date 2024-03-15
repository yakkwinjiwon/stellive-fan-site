package com.stellive.fansite.controller;

import com.stellive.fansite.domain.Notice;
import com.stellive.fansite.service.OffSiteService;
import com.stellive.fansite.service.YTApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/rest")
public class HomeController {

    private final YTApiService youtubeApiService;
    private final OffSiteService offSiteService;

    @GetMapping("")
    public String home() {
        log.info("home");

        offSiteService.updateAll();
        List<Notice> notices = offSiteService.findAllNotices();
        notices.forEach(notice -> log.info("notice={}", notice));

        return "ok";
    }
}

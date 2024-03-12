package com.stellive.fansite.controller;

import com.stellive.fansite.service.YTApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.ArrayList;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/rest")
public class HomeController {

    private final YTApiService youtubeApiService;

    @GetMapping("")
    public String home() throws InterruptedException {
        log.info("home");
//        twitterApiService.test();
//        youtubeApiService.test();


        return "ok";
    }
}

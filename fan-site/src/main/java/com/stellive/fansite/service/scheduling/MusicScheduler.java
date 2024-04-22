package com.stellive.fansite.service.scheduling;

import com.stellive.fansite.api.Youtube.VideoFetcher;
import com.stellive.fansite.domain.Video;
import com.stellive.fansite.domain.VideoType;
import com.stellive.fansite.repository.Video.VideoRepo;
import com.stellive.fansite.scraper.MusicScraper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MusicScheduler {

    private final MusicScraper musicScraper;
    private final VideoFetcher videoFetcher;

    private final VideoRepo videoRepo;

    public List<Video> updateMusics(ChromeDriver driver,
                                    WebDriverWait wait) {
        log.info("Update Musics");
        List<String> scrapedMusicIds = musicScraper.scrapeMusicIds(driver, wait);
        log.info("scraped MusicIds={}", scrapedMusicIds);
        List<Video> fetchedVideos = videoFetcher.fetchVideos(scrapedMusicIds, VideoType.MUSIC);
        List<Video> updatedMusics = videoRepo.save(fetchedVideos);
        log.info("updated Musics={}", updatedMusics);
        return updatedMusics;

    }
}

package com.stellive.fansite.client;

import com.stellive.fansite.domain.Video;
import com.stellive.fansite.dto.video.VideoContentDetails;
import com.stellive.fansite.dto.video.VideoItem;
import com.stellive.fansite.dto.video.VideoList;
import com.stellive.fansite.exceptions.ApiResponseException;
import com.stellive.fansite.service.channel.ChannelApiService;
import com.stellive.fansite.utils.ApiUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

import static com.stellive.fansite.utils.YoutubeApiConst.DELAY;
import static com.stellive.fansite.utils.YoutubeApiConst.MAX_ATTEMPTS;

@Component
@RequiredArgsConstructor
@Slf4j
public class VideoClient {

    private final RestTemplate restTemplate;
    private final ApiUtils apiUtils;

    private final ChannelApiService apiService;

    @Retryable(value = {RestClientException.class, ApiResponseException.class},
            maxAttempts = MAX_ATTEMPTS, backoff = @Backoff(delay = DELAY))
    public Video setDuration(Video video) {
        ResponseEntity<String> response = apiService.callVideo(video);
        VideoList list = apiUtils.mapToDto(response, VideoList.class);
        return setDuration(video, list);
    }

    private Video setDuration(Video video,
                              VideoList list) {
        String duration = Optional.ofNullable(list)
                .map(VideoList::getItems)
                .map(List::getFirst)
                .map(VideoItem::getContentDetails)
                .map(VideoContentDetails::getDuration)
                .orElse("PT0S");
        video.setDuration(Duration.parse(duration).getSeconds());
        return video;
    }
}

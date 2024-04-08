package com.stellive.fansite.api;

import com.stellive.fansite.domain.Video;
import com.stellive.fansite.dto.video.VideoList;
import com.stellive.fansite.utils.ApiUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import static com.stellive.fansite.utils.YoutubeApiConst.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class VideoConnector {

    private final RestTemplate restTemplate;
    private final ApiUtils apiUtils;

    @Retryable(value = {RestClientException.class}, maxAttempts = MAX_ATTEMPTS,
            backoff = @Backoff(delay = DELAY))
    public VideoList callVideo(Video video) {
        URI uri = getVideoUri(video);
        return restTemplate.getForEntity(uri, VideoList.class).getBody();
    }

    private URI getVideoUri(Video video) {
        return UriComponentsBuilder.fromHttpUrl(URL_VIDEO)
                .queryParam(PARAM_KEY, apiUtils.getYoutubeApiKey())
                .queryParam(PARAM_PART, PART_CONTENT_DETAILS)
                .queryParam(PARAM_ID, video.getExternalId())
                .build().toUri();
    }

}

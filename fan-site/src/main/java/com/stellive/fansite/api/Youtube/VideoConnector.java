package com.stellive.fansite.api.Youtube;

import com.stellive.fansite.dto.video.VideoList;
import com.stellive.fansite.utils.ApiKeyManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static com.stellive.fansite.utils.ApiConst.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class VideoConnector {

    private final RestTemplate restTemplate;
    private final ApiKeyManager keyManager;

    @Retryable(value = {RestClientException.class}, maxAttempts = MAX_ATTEMPTS,
            backoff = @Backoff(delay = DELAY, multiplier = MULTIPLIER, maxDelay = MAX_DELAY))
    public VideoList callVideo(List<String> externalIds) {
        URI uri = getVideoUri(externalIds);
        return restTemplate.getForEntity(uri, VideoList.class).getBody();
    }

    private URI getVideoUri(List<String> externalIds) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(URL_VIDEO)
                .queryParam(PARAM_KEY, keyManager.getYoutubeApiKey())
                .queryParam(PARAM_MAX_RESULTS, MAX_RESULTS_ALL)
                .queryParam(PARAM_PART, String.join(", ",
                        PART_CONTENT_DETAILS,
                        PART_SNIPPET,
                        PART_STATISTICS,
                        PART_LIVE_STREAMING_DETAILS));
        externalIds.forEach(id -> builder.queryParam(PARAM_ID, id));

        return builder.build().toUri();
    }

}

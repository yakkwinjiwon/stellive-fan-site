package com.stellive.fansite.service.video;

import com.stellive.fansite.domain.Video;
import com.stellive.fansite.utils.ApiUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import static com.stellive.fansite.utils.YoutubeApiConst.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class VideoApiService {

    private final RestTemplate restTemplate;
    private final ApiUtils apiUtils;

    public ResponseEntity<String> callVideo(Video video) {
        URI uri = getVideoUri(video);
        return restTemplate.getForEntity(uri, String.class);
    }

    private URI getVideoUri(Video video) {
        return UriComponentsBuilder.fromHttpUrl(URL_VIDEO)
                .queryParam(PARAM_KEY, apiUtils.getYoutubeApiKey())
                .queryParam(PARAM_PART, PART_CONTENT_DETAILS)
                .queryParam(PARAM_ID, video.getExternalId())
                .build().toUri();
    }

}

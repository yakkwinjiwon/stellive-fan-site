package com.stellive.fansite.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stellive.fansite.domain.Video;
import com.stellive.fansite.dto.video.VideoItem;
import com.stellive.fansite.dto.video.VideoList;
import com.stellive.fansite.exceptions.JsonParsingException;
import com.stellive.fansite.utils.ApiUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.Duration;
import java.util.List;

import static com.stellive.fansite.utils.YoutubeApiConst.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class VideoClient {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final ApiUtils apiUtils;

    public List<Video> setDuration(List<Video> videos) {
        videos.forEach(this::setDuration);
        return videos;
    }

    public Video setDuration(Video video) {

        ResponseEntity<String> response = fetchVideo(video);
        try {
            VideoList videoList = parseVideo(response);
            return setDurationFromList(video, videoList);
        } catch (JsonProcessingException | NullPointerException e) {
            throw new JsonParsingException("JSON parsing error", e);
        }
    }

    private ResponseEntity<String> fetchVideo(Video video) {
        URI uri = getVideoUri(video);
        return restTemplate.getForEntity(uri, String.class);
    }

    private URI getVideoUri(Video video) {
        return UriComponentsBuilder.fromHttpUrl(URL_VIDEO)
                .queryParam(PARAM_KEY, apiUtils.getYoutubeApiKey())
                .queryParam(PARAM_VIDEO_PART, PART_CONTENT_DETAILS)
                .queryParam(PARAM_VIDEO_ID, video.getExternalId())
                .build().toUri();
    }

    private VideoList parseVideo(ResponseEntity<String> response)
            throws JsonProcessingException {

        if (response.getStatusCode().is2xxSuccessful() && response.hasBody()) {
            String result = response.getBody();
            return objectMapper.readValue(result, VideoList.class);
        } else {
            log.error("YouTube API responded with status code: {}", response.getStatusCode());
            return new VideoList();
        }
    }

    private Video setDurationFromList(Video video,
                                      VideoList videoList) {

        VideoItem item = videoList.getItems().getFirst();
        String durationString = item.getContentDetails().getDuration();
        Duration duration = Duration.parse(durationString);
        video.setDuration(duration.getSeconds());

        return video;
    }
}

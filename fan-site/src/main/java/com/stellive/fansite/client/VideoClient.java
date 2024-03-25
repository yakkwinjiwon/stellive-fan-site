package com.stellive.fansite.client;

import com.stellive.fansite.domain.Video;
import com.stellive.fansite.dto.video.VideoContentDetails;
import com.stellive.fansite.dto.video.VideoItem;
import com.stellive.fansite.dto.video.VideoList;
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
import java.util.Optional;

import static com.stellive.fansite.utils.YoutubeApiConst.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class VideoClient {

    private final RestTemplate restTemplate;
    private final ApiUtils apiUtils;

    public List<Video> setDuration(List<Video> videos) {
        videos.forEach(this::setDuration);
        return videos;
    }

    private Video setDuration(Video video) {
        ResponseEntity<String> response = fetchVideo(video);
        VideoList list = apiUtils.parseResponse(response, VideoList.class);
        return setDuration(video, list);
    }

    private ResponseEntity<String> fetchVideo(Video video) {
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

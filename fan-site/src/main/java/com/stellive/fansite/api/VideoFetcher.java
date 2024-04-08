package com.stellive.fansite.api;

import com.stellive.fansite.domain.Video;
import com.stellive.fansite.dto.video.VideoContentDetails;
import com.stellive.fansite.dto.video.VideoItem;
import com.stellive.fansite.dto.video.VideoList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class VideoFetcher {

    private final VideoConnector videoConnector;

    public Video setVideoDuration(Video video) {
        VideoList list = videoConnector.callVideo(video);
        return setVideoDuration(video, list);
    }

    private Video setVideoDuration(Video video,
                                   VideoList list) {
        String duration = getDuration(list);
        video.setDuration(Duration.parse(duration).getSeconds());
        return video;
    }

    private String getDuration(VideoList list) {
        return Optional.ofNullable(list)
                .map(VideoList::getItems)
                .map(List::getFirst)
                .map(VideoItem::getContentDetails)
                .map(VideoContentDetails::getDuration)
                .orElse("PT0S");
    }
}

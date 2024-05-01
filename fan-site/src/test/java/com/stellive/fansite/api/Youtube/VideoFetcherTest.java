package com.stellive.fansite.api.Youtube;

import com.stellive.fansite.domain.LiveStatus;
import com.stellive.fansite.domain.Video;
import com.stellive.fansite.domain.VideoType;
import com.stellive.fansite.dto.video.VideoList;
import com.stellive.fansite.repository.Channel.ChannelRepo;
import com.stellive.fansite.utils.TestUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import static com.stellive.fansite.utils.TestConst.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class VideoFetcherTest {

    VideoFetcher videoFetcher;

    @Mock
    VideoConnector videoConnector;

    @Mock
    ChannelRepo channelRepo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        videoFetcher = new VideoFetcher(videoConnector, channelRepo);
    }

    @Test
    void fetchVideo() {
        // given
        VideoList videoList = TestUtils.getVideoList();
        when(videoConnector.callVideo(anyString())).thenReturn(videoList);

        Video video = Video.builder()
                .externalId(VIDEO_EXTERNAL_ID)
                .channel(channelRepo.findByExternalId(VIDEO_CHANNEL_ID).orElse(null))
                .duration(Duration.parse(VIDEO_DURATION).toSeconds())
                .scheduledStartTime(Instant.parse(VIDEO_SCHEDULED_START_TIME))
                .publishTime(Instant.parse(VIDEO_PUBLISHED_AT))
                .title(VIDEO_TITLE)
                .liveStatus(LiveStatus.fromString(VIDEO_LIVE_BROADCAST_CONTENT))
                .thumbnailUrl(VIDEO_THUMBNAIL_URL)
                .viewCount(VIDEO_VIEW_COUNT)
                .videoType(VideoType.VIDEO)
                .build();
        List<Video> videos = Arrays.asList(video);

        // when
        List<Video> fetchedVideos = videoFetcher.fetchVideo(VIDEO_EXTERNAL_ID, VideoType.VIDEO);

        // then
        assertThat(fetchedVideos).isEqualTo(videos);

    }

    @Test
    void fetchVideos() {
    }
}
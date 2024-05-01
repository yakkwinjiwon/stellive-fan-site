package com.stellive.fansite.api.Youtube;

import com.stellive.fansite.domain.*;
import com.stellive.fansite.dto.video.*;
import com.stellive.fansite.repository.Channel.ChannelRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static com.stellive.fansite.utils.AppConst.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class VideoFetcher {

    private final VideoConnector videoConnector;

    private final ChannelRepo channelRepo;

    public List<Video> fetchVideos(List<String> externalIds,
                                   VideoType videoType) {
        VideoList list = videoConnector.callVideo(externalId);
        return buildVideo(list, externalId, videoType);
    }

    public List<Video> fetchVideo(String externalId,
                                  VideoType videoType) {

    }

    private List<Video> buildVideo(VideoList list,
                                   String externalId,
                                   VideoType videoType) {
        List<VideoItem> items = getItems(list);
        return items.stream()
                .map(item -> Video.builder().externalId(externalId)
                        .channel(getChannel(item))
                        .duration(getDuration(item))
                        .scheduledStartTime(getScheduledStartTime(item))
                        .publishTime(getPublishTime(item))
                        .title(getTitle(item))
                        .liveStatus(getLiveStatus(item))
                        .thumbnailUrl(getThumbnailUrl(item))
                        .viewCount(getViewCount(item))
                        .videoType(determineVideoType(item, videoType))
                        .build())
                .toList();

    }

    private Channel getChannel(VideoItem item) {
        String channelId = Optional.ofNullable(item)
                .map(VideoItem::getSnippet)
                .map(VideoSnippet::getChannelId)
                .orElse("");
        return channelRepo.findByExternalId(channelId).orElse(null);
    }

    private List<VideoItem> getItems(VideoList list) {
        return Optional.ofNullable(list)
                .map(VideoList::getItems)
                .orElse(null);
    }

    private Long getDuration(VideoItem item) {
        String duration = Optional.ofNullable(item)
                .map(VideoItem::getContentDetails)
                .map(VideoContentDetails::getDuration)
                .orElse(DURATION_DEFAULT);
        return Duration.parse(duration).getSeconds();
    }

    private Instant getScheduledStartTime(VideoItem item) {
        String scheduledStartTime = Optional.ofNullable(item)
                .map(VideoItem::getLiveStreamingDetails)
                .map(VideoLiveStreamingDetails::getScheduledStartTime)
                .orElse(INSTANT_DEFAULT);
        return Instant.parse(scheduledStartTime);
    }

    private Instant getPublishTime(VideoItem item) {
        String publishedAt = Optional.ofNullable(item)
                .map(VideoItem::getSnippet)
                .map(VideoSnippet::getPublishedAt)
                .orElse(INSTANT_DEFAULT);
        return Instant.parse(publishedAt);
    }

    private String getChannelId(VideoItem item) {
        return Optional.ofNullable(item)
                .map(VideoItem::getSnippet)
                .map(VideoSnippet::getChannelId)
                .orElse("");
    }

    private String getTitle(VideoItem item) {
        return Optional.ofNullable(item)
                .map(VideoItem::getSnippet)
                .map(VideoSnippet::getTitle)
                .orElse("");
    }

    private LiveStatus getLiveStatus(VideoItem item) {
        String liveBroadcastContent = Optional.ofNullable(item)
                .map(VideoItem::getSnippet)
                .map(VideoSnippet::getLiveBroadcastContent)
                .orElse("");
        return LiveStatus.fromString(liveBroadcastContent);
    }

    private String getThumbnailUrl(VideoItem item) {
        return Optional.ofNullable(item)
                .map(VideoItem::getSnippet)
                .map(VideoSnippet::getThumbnails)
                .map(VideoThumbnails::getHigh)
                .map(VideoThumbnail::getUrl)
                .orElse("");
    }

    private Integer getViewCount(VideoItem item) {
        return Optional.ofNullable(item)
                .map(VideoItem::getStatistics)
                .map(VideoStatistics::getViewCount)
                .orElse(0);
    }

    private VideoType determineVideoType(VideoItem item,
                                         VideoType videoType) {
        if (videoType == VideoType.UNKNOWN) {
            return (getDuration(item) <= 60) ? VideoType.SHORTS : VideoType.VIDEO;
        }
        return videoType;
    }
}

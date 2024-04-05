package com.stellive.fansite.client;

import com.stellive.fansite.domain.Channel;
import com.stellive.fansite.domain.YoutubeChannel;
import com.stellive.fansite.dto.channel.*;
import com.stellive.fansite.exceptions.ApiResponseException;
import com.stellive.fansite.exceptions.EmptyItemException;
import com.stellive.fansite.repository.Video.VideoRepo;
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

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class ChannelClient {

    private final RestTemplate restTemplate;
    private final ApiUtils apiUtils;

    private final ChannelApiService apiService;

    private final VideoRepo videoRepo;

    @Retryable(value = {RestClientException.class, ApiResponseException.class},
            maxAttempts = MAX_ATTEMPTS, backoff = @Backoff(delay = DELAY))
    public Channel fetchChannel(YoutubeChannel youtubeChannel) {
        ResponseEntity<String> response = apiService.callChannel(youtubeChannel);
        ChannelList list = apiUtils.mapToDto(response, ChannelList.class);
        return buildChannel(list, youtubeChannel);
    }

    private Channel buildChannel(ChannelList list,
                                 YoutubeChannel youtubeChannel) {
        ChannelItem item = Optional.ofNullable(list)
                .map(ChannelList::getItems)
                .map(List::getFirst)
                .orElseThrow(() -> new EmptyItemException("Channel item not found, Id=" +
                        youtubeChannel.getChannelId()));

        return Channel.builder()
                .id(youtubeChannel.getId())
                .externalId(youtubeChannel.getChannelId())
                .videos(videoRepo.findByChannelId(youtubeChannel.getId()))
                .handle(Optional.ofNullable(item)
                        .map(ChannelItem::getSnippet)
                        .map(ChannelSnippet::getCustomUrl)
                        .orElse(""))
                .thumbnailUrl(Optional.ofNullable(item)
                        .map(ChannelItem::getSnippet)
                        .map(ChannelSnippet::getThumbnails)
                        .map(ChannelThumbnails::getHigh)
                        .map(ChannelThumbnail::getUrl)
                        .orElse(""))
                .bannerUrl(Optional.ofNullable(item)
                        .map(ChannelItem::getBrandingSettings)
                        .map(ChannelBrandingSettings::getImage)
                        .map(ChannelImage::getBannerExternalUrl)
                        .orElse(""))
                .build();
    }

}
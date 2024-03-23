package com.stellive.fansite.client;

import com.stellive.fansite.domain.Channel;
import com.stellive.fansite.domain.YoutubeChannel;
import com.stellive.fansite.dto.channel.*;
import com.stellive.fansite.exceptions.ApiResponseException;
import com.stellive.fansite.exceptions.ResponseParsingException;
import com.stellive.fansite.repository.Video.VideoRepo;
import com.stellive.fansite.utils.ApiUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import static com.stellive.fansite.utils.YoutubeApiConst.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class ChannelClient {

    private final VideoRepo videoRepo;

    private final RestTemplate restTemplate;
    private final ApiUtils apiUtils;

    @Retryable(value = {RestClientException.class, ResponseParsingException.class,
            ApiResponseException.class},
            maxAttempts = 1, backoff = @Backoff(delay = 1000))
    public Channel getChannel(YoutubeChannel youtubeChannel) {
        ResponseEntity<String> response = fetchChannel(youtubeChannel);
        ChannelList list = apiUtils.parseResponse(response, ChannelList.class);
        return buildChannel(list, youtubeChannel);
    }

    private ResponseEntity<String> fetchChannel(YoutubeChannel youtubeChannel) {
        URI uri = getChannelUri(youtubeChannel);
        return restTemplate.getForEntity(uri, String.class);
    }

    private URI getChannelUri(YoutubeChannel youtubeChannel) {
        return UriComponentsBuilder.fromHttpUrl(URL_CHANNEL)
                .queryParam(PARAM_KEY, apiUtils.getYoutubeApiKey())
                .queryParam(PARAM_CHANNEL_PART,
                        PART_SNIPPET + ", " +
                                PART_BRANDING_SETTINGS)
                .queryParam(PARAM_CHANNEL_ID, youtubeChannel.getChannelId())
                .build().toUri();
    }

    private Channel buildChannel(ChannelList list, YoutubeChannel youtubeChannel) {
        ChannelItem item = Optional.ofNullable(list)
                .map(ChannelList::getItems)
                .map(List::getFirst)
                .orElse(null);

        return Channel.builder()
                .id(youtubeChannel.getId())
                .externalId(youtubeChannel.getChannelId())
                .videos(videoRepo.findByChannelId(youtubeChannel.getId()))
                .handle(Optional.ofNullable(item)
                        .map(ChannelItem::getSnippet)
                        .map(ChannelSnippet::getCustomUrl)
                        .orElse(null))
                .thumbnailUrl(Optional.ofNullable(item)
                        .map(ChannelItem::getSnippet)
                        .map(ChannelSnippet::getThumbnails)
                        .map(ChannelThumbnails::getHigh)
                        .map(ChannelThumbnail::getUrl)
                        .orElse(null))
                .bannerUrl(Optional.ofNullable(item)
                        .map(ChannelItem::getBrandingSettings)
                        .map(ChannelBrandingSettings::getImage)
                        .map(ChannelImage::getBannerExternalUrl)
                        .orElse(null))
                .build();
    }

}
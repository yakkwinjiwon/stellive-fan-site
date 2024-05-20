package com.stellive.fansite.api.Youtube;

import com.stellive.fansite.domain.Channel;
import com.stellive.fansite.domain.YoutubeChannel;
import com.stellive.fansite.dto.channel.*;
import com.stellive.fansite.exceptions.EmptyItemException;
import com.stellive.fansite.repository.Video.VideoRepo;
import com.stellive.fansite.utils.AppConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.stellive.fansite.utils.AppConst.*;

@Service
@RequiredArgsConstructor
public class ChannelFetcher {

    private final ChannelConnector channelConnector;

    private final VideoRepo videoRepo;

    public Channel fetchChannel(YoutubeChannel youtubeChannel) {
        ChannelList list = channelConnector.callChannel(youtubeChannel);
        return buildChannel(list, youtubeChannel);
    }

    private Channel buildChannel(ChannelList list,
                                 YoutubeChannel youtubeChannel) {
        ChannelItem item = getItem(list, youtubeChannel);
        return Channel.builder()
                .id(youtubeChannel.getId())
                .externalId(youtubeChannel.getChannelId())
                .videos(videoRepo.findByChannelId(youtubeChannel.getId()))
                .handle(getHandle(item))
                .thumbnailUrl(getThumbnailUrl(item))
                .bannerUrl(getBannerUrl(item))
                .build();
    }

    private ChannelItem getItem(ChannelList list,
                                YoutubeChannel youtubeChannel) {
        return Optional.ofNullable(list)
                .map(ChannelList::getItems)
                .map(List::getFirst)
                .orElseThrow(() -> new EmptyItemException("Channel item not found, Channel=" + youtubeChannel));
    }

    private String getBannerUrl(ChannelItem item) {
        return Optional.ofNullable(item)
                .map(ChannelItem::getBrandingSettings)
                .map(ChannelBrandingSettings::getImage)
                .map(ChannelImage::getBannerExternalUrl)
                .orElse(STRING_DEFAULT);
    }

    private String getThumbnailUrl(ChannelItem item) {
        return Optional.ofNullable(item)
                .map(ChannelItem::getSnippet)
                .map(ChannelSnippet::getThumbnails)
                .map(ChannelThumbnails::getHigh)
                .map(ChannelThumbnail::getUrl)
                .orElse(STRING_DEFAULT);
    }

    private String getHandle(ChannelItem item) {
        return Optional.ofNullable(item)
                .map(ChannelItem::getSnippet)
                .map(ChannelSnippet::getCustomUrl)
                .orElse(STRING_DEFAULT);
    }

}
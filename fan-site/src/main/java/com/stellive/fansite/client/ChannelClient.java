package com.stellive.fansite.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stellive.fansite.domain.Channel;
import com.stellive.fansite.domain.YoutubeChannel;
import com.stellive.fansite.dto.channel.ChannelImage;
import com.stellive.fansite.dto.channel.ChannelItem;
import com.stellive.fansite.dto.channel.ChannelList;
import com.stellive.fansite.exceptions.JsonParsingException;
import com.stellive.fansite.repository.Video.VideoRepo;
import com.stellive.fansite.utils.ApiUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import static com.stellive.fansite.utils.YoutubeApiConst.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class ChannelClient {

    private final VideoRepo videoRepo;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final ApiUtils apiUtils;

    public Channel getChannel(YoutubeChannel youtubeChannel) {
        ResponseEntity<String> response = fetchChannel(youtubeChannel);
        try {
            return parseChannel(youtubeChannel, response);
        } catch (JsonProcessingException | NullPointerException e) {
            throw new JsonParsingException("JSON parsing error", e);
        }
    }

    private ResponseEntity<String> fetchChannel(YoutubeChannel stella) {
        URI uri = getChannelUri(stella);
        return restTemplate.getForEntity(uri, String.class);
    }

    private URI getChannelUri(YoutubeChannel stella) {
        return UriComponentsBuilder.fromHttpUrl(URL_CHANNEL)
                .queryParam(PARAM_KEY, apiUtils.getYoutubeApiKey())
                .queryParam(PARAM_CHANNEL_PART,
                        PART_SNIPPET + ", " +
                        PART_BRANDING_SETTINGS)
                .queryParam(PARAM_CHANNEL_ID, stella.getChannelId())
                .build().toUri();
    }

    private Channel parseChannel(YoutubeChannel stella,
                                 ResponseEntity<String> response)
            throws JsonProcessingException {
        if (response.getStatusCode().is2xxSuccessful() && response.hasBody()) {
            String result = response.getBody();
            ChannelList channelList = objectMapper.readValue(result, ChannelList.class);
            return buildChannel(stella, channelList);
        } else {
            log.error("YouTube API responded with status code: {}", response.getStatusCode());
            return new Channel();
        }
    }

    private Channel buildChannel(YoutubeChannel stella,
                                 ChannelList channelList) {
        ChannelItem item = channelList.getItems().getFirst();
        ChannelImage image = item.getBrandingSettings().getImage();

        return Channel.builder()
                .id(stella.getId())
                .externalId(item.getId())
                .videos(videoRepo.findByChannelId(stella.getId()))
                .handle(item.getSnippet().getCustomUrl())
                .thumbnailUrl(item.getSnippet().getThumbnails().getHigh().getUrl())
                .bannerUrl((image != null) ? image.getBannerExternalUrl() : null)
                .build();
    }

}
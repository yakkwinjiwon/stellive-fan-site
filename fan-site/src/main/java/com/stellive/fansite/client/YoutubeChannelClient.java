package com.stellive.fansite.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stellive.fansite.domain.Channel;
import com.stellive.fansite.client.channellist.ChannelList;
import com.stellive.fansite.client.channellist.ChannelItem;
import com.stellive.fansite.domain.ChannelId;
import com.stellive.fansite.exceptions.JsonParsingException;
import com.stellive.fansite.repository.YoutubeRepository;
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
public class YoutubeChannelClient {

    private final YoutubeRepository repository;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final ApiUtils apiUtils;

    public Channel getChannel(ChannelId channelId) {
        ResponseEntity<String> response = fetchChannel(channelId.getExternalId());
        try {
            Channel channel = parseChannel(response, channelId.getId());
            log.info("Fetched Youtube Channel={}", channel);
            return channel;
        } catch (JsonProcessingException e) {
            throw new JsonParsingException("JSON parsing error", e);
        }
    }

    private ResponseEntity<String> fetchChannel(String externalId) {
        URI uri = getChannelUri(externalId);
        return restTemplate.getForEntity(uri, String.class);
    }

    private URI getChannelUri(String externalId) {
        return UriComponentsBuilder.fromHttpUrl(URL_CHANNEL)
                .queryParam(PARAM_CHANNEL_KEY, apiUtils.getYoutubeApiKey())
                .queryParam(PARAM_CHANNEL_PART, PART_SNIPPET)
                .queryParam(PARAM_CHANNEL_ID, externalId)
                .build().toUri();
    }

    private Channel parseChannel(ResponseEntity<String> response, Long id) throws JsonProcessingException {
        if (response.getStatusCode().is2xxSuccessful() && response.hasBody()) {
            String result = response.getBody();
            ChannelList channelList = objectMapper.readValue(result, ChannelList.class);
            return buildChannel(channelList, id);
        } else {
            log.error("YouTube API responded with status code: {}", response.getStatusCode());
            return new Channel();
        }
    }

    private Channel buildChannel(ChannelList channelList, Long id) {
        ChannelItem item = channelList.getItems().getFirst();
        repository.findVideosByChannelId(id);
        return Channel.builder()
                .id(id)
                .externalId(item.getId())
                .handle(item.getSnippet().getCustomUrl())
                .thumbnailUrl(item.getSnippet().getThumbnails().getHigh().getUrl())
                .build();
    }
}


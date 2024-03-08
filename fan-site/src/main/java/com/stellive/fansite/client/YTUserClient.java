package com.stellive.fansite.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stellive.fansite.dto.channellist.ChannelList;
import com.stellive.fansite.dto.channellist.ChannelItem;
import com.stellive.fansite.domain.Stella;
import com.stellive.fansite.domain.YTUser;
import com.stellive.fansite.exceptions.JsonParsingException;
import com.stellive.fansite.repository.YTRepository;
import com.stellive.fansite.utils.ApiUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import static com.stellive.fansite.utils.YTApiConst.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class YTUserClient {

    private final YTRepository repository;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final ApiUtils apiUtils;

    public YTUser getYTUser(Stella stella) {
        ResponseEntity<String> response = fetchChannel(stella);
        try {
            YTUser user = parseChannel(stella, response);
            log.info("Fetched YoutubeChannel={}", user);
            return user;
        } catch (JsonProcessingException e) {
            throw new JsonParsingException("JSON parsing error", e);
        }
    }

    private ResponseEntity<String> fetchChannel(Stella stella) {
        URI uri = getChannelUri(stella);
        return restTemplate.getForEntity(uri, String.class);
    }

    private URI getChannelUri(Stella stella) {
        return UriComponentsBuilder.fromHttpUrl(URL_CHANNEL)
                .queryParam(PARAM_CHANNEL_KEY, apiUtils.getYoutubeApiKey())
                .queryParam(PARAM_CHANNEL_PART, PART_SNIPPET)
                .queryParam(PARAM_CHANNEL_ID, stella.getYoutubeId())
                .build().toUri();
    }

    private YTUser parseChannel(Stella stella, ResponseEntity<String> response) throws JsonProcessingException {
        if (response.getStatusCode().is2xxSuccessful() && response.hasBody()) {
            String result = response.getBody();
            ChannelList channelList = objectMapper.readValue(result, ChannelList.class);
            return buildYTUser(stella, channelList);
        } else {
            log.error("YouTube API responded with status code: {}", response.getStatusCode());
            return new YTUser();
        }
    }

    private YTUser buildYTUser(Stella stella, ChannelList channelList) {
        ChannelItem item = channelList.getItems().getFirst();
        return YTUser.builder()
                .id(stella.getId())
                .name(stella.getFullName())
                .externalId(item.getId())
                .handle(item.getSnippet().getCustomUrl())
                .thumbnailUrl(item.getSnippet().getThumbnails().getHigh().getUrl())
                .videos(repository.findYTVideosByYTUserId(stella.getId()))
                .build();
    }

}


package com.stellive.fansite.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stellive.fansite.domain.Stella;
import com.stellive.fansite.domain.YTMusic;
import com.stellive.fansite.dto.channel.ChannelItem;
import com.stellive.fansite.dto.channel.ChannelList;
import com.stellive.fansite.exceptions.JsonParsingException;
import com.stellive.fansite.repository.YTRepo;
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
@Slf4j
@RequiredArgsConstructor
public class YTMusicClient {
    private final YTRepo repo;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final ApiUtils apiUtils;

    public YTMusic getYTMusic(Stella stella, int index) {
        ResponseEntity<String> response = fetchPlaylistsItem(stella, index);
        try {
            YTMusic music = parsePlaylistsItem(stella, response);
            log.info("Fetched YoutubeMusic={}", music);
            return music;
        } catch (JsonProcessingException | NullPointerException e) {
            throw new JsonParsingException("JSON parsing error", e);
        }
    }

    private ResponseEntity<String> fetchPlaylistsItem(Stella stella, int index) {
        URI uri = getPlaylistsItemUri(stella, index);
        return restTemplate.getForEntity(uri, String.class);
    }

    private URI getPlaylistsItemUri(Stella stella, int index) {
        return UriComponentsBuilder.fromHttpUrl(URL_PLAYLIST_ITEM)
                .queryParam(PARAM_KEY, apiUtils.getYoutubeApiKey())
                .queryParam(PARAM_PLAYLIST_ITEM_PART, PART_SNIPPET)
                .queryParam(PARAM_PLAYLIST_ITEM_PLAYLIST_ID, stella.getCoverSongPLIds().get(index))
                .build().toUri();
    }

    private YTMusic parsePlaylistsItem(Stella stella, ResponseEntity<String> response) throws JsonProcessingException {
        if (response.getStatusCode().is2xxSuccessful() && response.hasBody()) {
            String result = response.getBody();
            ChannelList channelList = objectMapper.readValue(result, PlalistItemsList.class);
            return buildYTMusic(stella, channelList);
        } else {
            log.error("YouTube API responded with status code: {}", response.getStatusCode());
            return new YTMusic();
        }
    }

    private YTMusic buildYTMusic(Stella stella, ChannelList channelList) {
        ChannelItem item = channelList.getItems().getFirst();
        return YTMusic.builder()
                .id(stella.getId())
                .name(stella.getFullName())
                .externalId(item.getId())
                .handle(item.getSnippet().getCustomUrl())
                .thumbnailUrl(item.getSnippet().getThumbnails().getHigh().getUrl())
                .videos(repo.findYTVideosByYTMusicId(stella.getId()))
                .build();
    }

}

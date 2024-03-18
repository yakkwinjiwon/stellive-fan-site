package com.stellive.fansite.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stellive.fansite.domain.YoutubeChannel;
import com.stellive.fansite.domain.VideoType;
import com.stellive.fansite.domain.Channel;
import com.stellive.fansite.domain.Video;
import com.stellive.fansite.dto.playlistitems.PlaylistItemsItem;
import com.stellive.fansite.dto.playlistitems.PlaylistItemsList;
import com.stellive.fansite.dto.playlistitems.PlaylistItemsSnippet;
import com.stellive.fansite.exceptions.JsonParsingException;
import com.stellive.fansite.repository.Channel.ChannelRepo;
import com.stellive.fansite.utils.ApiUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.stellive.fansite.utils.YoutubeApiConst.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class MusicClient {

    private final ChannelRepo channelRepo;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final ApiUtils apiUtils;

    public List<Video> getMusics(YoutubeChannel stella, String playlistId) {
        ResponseEntity<String> response = fetchPlaylistsItem(playlistId);
        try {
            List<Video> musics = parsePlaylistsItem(stella, response);
            log.info("Fetched Youtube Musics={}", musics);
            return musics;
        } catch (JsonProcessingException | NullPointerException e) {
            throw new JsonParsingException("JSON parsing error", e);
        }
    }

    private ResponseEntity<String> fetchPlaylistsItem(String playlistId) {
        URI uri = getPlaylistsItemUri(playlistId);
        return restTemplate.getForEntity(uri, String.class);
    }

    private URI getPlaylistsItemUri(String playlistId) {
        return UriComponentsBuilder.fromHttpUrl(URL_PLAYLIST_ITEMS)
                .queryParam(PARAM_KEY, apiUtils.getYoutubeApiKey())
                .queryParam(PARAM_PLAYLIST_ITEMS_PART, PART_SNIPPET)
                .queryParam(PARAM_PLAYLIST_ITEMS_PLAYLIST_ID, playlistId)
                .build().toUri();
    }

    private List<Video> parsePlaylistsItem(YoutubeChannel stella, ResponseEntity<String> response) throws JsonProcessingException {
        if (response.getStatusCode().is2xxSuccessful() && response.hasBody()) {
            String result = response.getBody();
            PlaylistItemsList playlistItemsList = objectMapper.readValue(result, PlaylistItemsList.class);
            return buildMusics(stella, playlistItemsList);
        } else {
            log.error("YouTube API responded with status code: {}", response.getStatusCode());
            return Collections.emptyList();
        }
    }

    private List<Video> buildMusics(YoutubeChannel stella, PlaylistItemsList playlistItemsList) {
        List<Video> musics = new ArrayList<>();
        List<PlaylistItemsItem> items = playlistItemsList.getItems();

        items.forEach(item -> {
            PlaylistItemsSnippet snippet = item.getSnippet();
            Video music = Video.builder()
                    .videoType(VideoType.MUSIC)
                    .channel(channelRepo.findById(stella.getId()).orElseGet(Channel::new))
                    .externalId(snippet.getResourceId().getVideoId())
                    .title(snippet.getTitle())
                    .thumbnailUrl(snippet.getThumbnails().getHigh().getUrl())
                    .publishTime(Instant.parse(snippet.getPublishedAt()))
                    .build();
            musics.add(music);
        });

        return musics;
    }
}

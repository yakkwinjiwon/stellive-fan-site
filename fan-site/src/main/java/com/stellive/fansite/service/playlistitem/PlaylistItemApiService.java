package com.stellive.fansite.service.playlistitem;

import com.stellive.fansite.utils.ApiUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

import static com.stellive.fansite.utils.YoutubeApiConst.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class PlaylistItemApiService {

    private final RestTemplate restTemplate;
    private final ApiUtils apiUtils;

    public ResponseEntity<String> callPlaylistItem(String playlistId,
                                                    Integer maxResults,
                                                    String nextPageToken) {
        URI uri = getPlaylistItemUri(playlistId, maxResults, nextPageToken);
        return restTemplate.getForEntity(uri, String.class);
    }

    private URI getPlaylistItemUri(String playlistId,
                                   Integer maxResults,
                                   String nextPageToken) {
        return UriComponentsBuilder.fromHttpUrl(URL_PLAYLIST_ITEMS)
                .queryParam(PARAM_KEY, apiUtils.getYoutubeApiKey())
                .queryParam(PARAM_PART, PART_SNIPPET)
                .queryParam(PARAM_PLAYLIST_ID, playlistId)
                .queryParam(PARAM_MAX_RESULTS, maxResults)
                .queryParamIfPresent(PARAM_PAGE_TOKEN, Optional.ofNullable(nextPageToken))
                .build().toUri();
    }

}

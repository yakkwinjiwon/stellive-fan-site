package com.stellive.fansite.service.channel;

import com.stellive.fansite.domain.YoutubeChannel;
import com.stellive.fansite.utils.ApiUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import static com.stellive.fansite.utils.YoutubeApiConst.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChannelApiService {

    private final RestTemplate restTemplate;
    private final ApiUtils apiUtils;

    public ResponseEntity<String> callChannel(YoutubeChannel youtubeChannel) {
        URI uri = getChannelUri(youtubeChannel);
        return restTemplate.getForEntity(uri, String.class);
    }

    private URI getChannelUri(YoutubeChannel youtubeChannel) {
        return UriComponentsBuilder.fromHttpUrl(URL_CHANNEL)
                .queryParam(PARAM_KEY, apiUtils.getYoutubeApiKey())
                .queryParam(PARAM_PART,
                        PART_SNIPPET + ", " +
                                PART_BRANDING_SETTINGS)
                .queryParam(PARAM_ID, youtubeChannel.getChannelId())
                .build().toUri();
    }
}

package com.stellive.fansite.api;

import com.stellive.fansite.domain.YoutubeChannel;
import com.stellive.fansite.dto.channel.ChannelList;
import com.stellive.fansite.exceptions.ApiResponseException;
import com.stellive.fansite.utils.ApiUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import static com.stellive.fansite.utils.YoutubeApiConst.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChannelConnector {

    private final RestTemplate restTemplate;
    private final ApiUtils apiUtils;

    @Retryable(value = {RestClientException.class}, maxAttempts = MAX_ATTEMPTS,
            backoff = @Backoff(delay = DELAY))
    public ChannelList callChannel(YoutubeChannel youtubeChannel) {
        URI uri = getChannelUri(youtubeChannel);
        return restTemplate.getForEntity(uri, ChannelList.class).getBody();
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

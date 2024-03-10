package com.stellive.fansite.client;

import com.stellive.fansite.domain.TWUser;
import com.twitter.clientlib.ApiException;
import com.twitter.clientlib.api.TwitterApi;
import com.twitter.clientlib.model.TweetCreateRequest;
import com.twitter.clientlib.model.TweetCreateResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;

import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class TWUserClient {

    private final TwitterApi twitterApi;

    public TWUser getTWUser() {
        List<String> ids = Arrays.asList("StelLive_kr");

        try{
            TweetCreateResponse result = twitterApi.tweets().createTweet(new TweetCreateRequest())
                    .execute();

            log.info("result={}", result);
        } catch (ApiException e) {
            throw new RestClientException("Twitter Api Error", e);
        }
        return null;
    }
}

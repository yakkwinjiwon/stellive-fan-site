package com.stellive.fansite.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;

import static com.stellive.fansite.utils.YoutubeApiConst.DELAY;
import static com.stellive.fansite.utils.YoutubeApiConst.MAX_ATTEMPTS;

@Component
@Slf4j
public class TestClass {

    @Retryable(value = {RestClientException.class}, maxAttempts = MAX_ATTEMPTS,
            backoff = @Backoff(delay = DELAY))
    public void callApi() {
        log.info("callApi");
        throw new RestClientException("Error");
    }
}

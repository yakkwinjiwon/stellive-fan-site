package com.stellive.fansite.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stellive.fansite.domain.StellaChannel;
import com.stellive.fansite.domain.YoutubeChannel;
import com.stellive.fansite.exceptions.ApiResponseException;
import com.stellive.fansite.exceptions.EmptyItemException;
import com.stellive.fansite.exceptions.ResponseParsingException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;

import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Slf4j
@Getter
@Component
@RequiredArgsConstructor
public class ApiUtils {

    private final ObjectMapper objectMapper;

    @Value("${youtube-api-key}")
    private String youtubeApiKey;

    // StellaChannel의 각 값에 대해 주어진 작업을 실행하는 메서드
    public void executeForEachChannel(Consumer<YoutubeChannel> channelOperation) {
        Arrays.stream(StellaChannel.values())
                .forEach(youtubeChannel -> {
                    executeWithHandling(() -> {
                        channelOperation.accept(youtubeChannel);
                        return null;
                    });
                });
    }

    public <T> void executeWithHandling(Supplier<T> supplier) {
        try {
            supplier.get();
        } catch (EmptyItemException e) {
            log.error("Error empty item", e);
        } catch (RestClientException e) {
            log.error("Error calling API", e);
        } catch (ApiResponseException e) {
            log.error("Error in API response", e);
        } catch (ResponseParsingException e) {
            log.error("Error parsing API response", e);
        } catch (Exception e) {
            log.error("Unexpected error", e);
        }
    }


}
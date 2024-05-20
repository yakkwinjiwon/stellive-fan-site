package com.stellive.fansite.utils;

import com.stellive.fansite.domain.StellaYoutubeChannel;
import com.stellive.fansite.domain.YoutubeChannel;
import com.stellive.fansite.exceptions.ApiResponseException;
import com.stellive.fansite.exceptions.EmptyItemException;
import com.stellive.fansite.exceptions.ResponseParsingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestClientException;

import java.util.*;
import java.util.function.Consumer;

@Slf4j
public class ApiUtils {

    // StellaChannel의 각 값에 대해 주어진 작업을 실행하는 메서드
    public static void executeForEachChannel(Consumer<YoutubeChannel> channelOperation) {
        Arrays.stream(StellaYoutubeChannel.values())
                .forEach(youtubeChannel ->
                        executeWithHandling(() -> channelOperation.accept(youtubeChannel))
                );
    }

    public static void executeWithHandling(Runnable task) {
        try {
            task.run();
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
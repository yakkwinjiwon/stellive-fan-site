package com.stellive.fansite.utils;

import com.stellive.fansite.domain.StellaChannel;
import com.stellive.fansite.domain.YoutubeChannel;
import com.stellive.fansite.exceptions.ApiResponseException;
import com.stellive.fansite.exceptions.EmptyItemException;
import com.stellive.fansite.exceptions.ExtractFieldsException;
import com.stellive.fansite.exceptions.ResponseParsingException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Component
@Getter
@Slf4j
public class ApiUtils {

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

    /**
     * U 리스트에서 F의 필드명으로 추출
     */
    public <T, U> List<T> extractFields(List<U> items, String fieldName, Class<T> fieldType) {
        if(items.isEmpty()){
            return new ArrayList<>();
        }

        Class<?> clazz = items.get(0).getClass();

        try{
            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);

            return items.stream()
                    .map(item -> fieldType.cast(getValue(item, field)))
                    .toList();
        } catch (NoSuchFieldException e) {
            throw new ExtractFieldsException(e);
        }

    }

    private Object getValue(Object object, Field field) {
        try {
            return field.get(object);
        } catch (IllegalAccessException e) {
            throw new ExtractFieldsException(e);
        }
    }

}
package com.stellive.fansite.utils;

import com.stellive.fansite.exceptions.ExtractFieldsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class AppUtils {

    /**
     * U 리스트에서 F의 필드명으로 추출
     */
    public static <T, U> List<T> extractFields(List<U> items, String fieldName, Class<T> fieldType) {
        if(items.isEmpty()){
            return new ArrayList<>();
        }
        Class<?> clazz = items.getFirst().getClass();

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

    private static Object getValue(Object object, Field field) {
        try {
            return field.get(object);
        } catch (IllegalAccessException e) {
            throw new ExtractFieldsException(e);
        }
    }

    /**
     * 문자열을 Instant로 변환
     */
    public static Instant stringToInstant(String time, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return LocalDate.parse(time, formatter)
                .atStartOfDay()
                .atZone(ZoneId.of("UTC"))
                .toInstant();
    }
}

package com.stellive.fansite.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum ChannelId {

    KANNA(1L, "UCKzfyYWHQ92z_2jUcSABM8Q"),
    YUNI(2L, "UClbYIn9LDbbFZ9w2shX3K0g"),
    HINA(3L, "UC1afpiIuBDcjYlmruAa0HiA"),
    MASHIRO(4L, "UC_eeSpMBz8PG4ssdBPnP07g"),
    LIZE(5L, "UC7-m6jQLinZQWIbwm9W-1iw"),
    TABI(6L, "UCAHVQ44O81aehLWfy9O6Elw"),
    OFFICIAL(7L, "UC2b4WRE5BZ6SIUWBeJU8rwg");

    private final Long id;
    private final String externalId;

    public static String findExternalIdById(Long id) {
        return Arrays.stream(ChannelId.values())
                .filter(channel -> channel.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No Matching channel for id: " + id))
                .getExternalId();
    }

}

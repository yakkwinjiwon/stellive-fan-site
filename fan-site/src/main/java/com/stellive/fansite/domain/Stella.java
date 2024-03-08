package com.stellive.fansite.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum Stella {

    KANNA(1L, "칸나", "아이리 칸나",
            "UCKzfyYWHQ92z_2jUcSABM8Q"
            ),
    YUNI(2L, "유니", "아야츠노 유니",
            "UClbYIn9LDbbFZ9w2shX3K0g"),
    HINA(3L, "히나", "시라유키 히나",
            "UC1afpiIuBDcjYlmruAa0HiA"),
    MASHIRO(4L, "마시로", "네네코 마시로",
            "UC_eeSpMBz8PG4ssdBPnP07g"),
    LIZE(5L, "리제", "아카네 리제",
            "UC7-m6jQLinZQWIbwm9W-1iw"),
    TABI(6L, "타비", "아라하시 타비",
            "UCAHVQ44O81aehLWfy9O6Elw"),
    OFFICIAL(101L, "Stellive Official", "Stellive Official",
            "UC2b4WRE5BZ6SIUWBeJU8rwg");

    private final Long id;
    private final String firstName;
    private final String fullName;
    private final String youtubeId;

    public static String findYoutubeIdById(Long id) {
        return Arrays.stream(Stella.values())
                .filter(channel -> channel.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No Matching channel for id: " + id))
                .getYoutubeId();
    }

}

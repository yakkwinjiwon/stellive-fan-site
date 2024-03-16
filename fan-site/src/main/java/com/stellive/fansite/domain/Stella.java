package com.stellive.fansite.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
@AllArgsConstructor
public enum Stella {

    KANNA(1L, "칸나", "아이리 칸나",
            "UCKzfyYWHQ92z_2jUcSABM8Q",
            Arrays.asList("PLOPBV2KAIYnsNM0omb4ub9hofPnDHz20P",
                    "PLOPBV2KAIYnt1NJ0QCkfCqMRc00lfcjFa",
                    "PLOPBV2KAIYnthM0P8AmQ-wbBYIjkSRY4D")),

    YUNI(2L, "유니", "아야츠노 유니",
            "UClbYIn9LDbbFZ9w2shX3K0g",
            Arrays.asList("PL3HtH_xx9h_7ZGoZ9zMUQ-MPumwe21_cc",
                    "PL3HtH_xx9h_4ulddfVG8DdD_EwKUMBqvL")),

    HINA(3L, "히나", "시라유키 히나",
            "UC1afpiIuBDcjYlmruAa0HiA",
            Arrays.asList("PLzdLDJsHzz2NiuwjyW6QgSck4PrwlSyOc")),

    MASHIRO(4L, "마시로", "네네코 마시로",
            "UC_eeSpMBz8PG4ssdBPnP07g",
            Arrays.asList("PLWwhuXFHGLvhgZZb5_rmQEMI1B0ysKJxG")),

    LIZE(5L, "리제", "아카네 리제",
            "UC7-m6jQLinZQWIbwm9W-1iw",
            Arrays.asList("PL-DHk0WpiRNSM5oI19ImJ8sSV65mnGseX")),

    TABI(6L, "타비", "아라하시 타비",
            "UCAHVQ44O81aehLWfy9O6Elw",
            Arrays.asList("PLbIDsfX2JRA0oawGN209gpd_nz9IMvUlb")),

    OFFICIAL(101L, "Stellive Official", "Stellive Official",
            "UC2b4WRE5BZ6SIUWBeJU8rwg",
            Arrays.asList(""));

    private final Long id;
    private final String firstName;
    private final String fullName;
    private final String youtubeId;
    private final List<String> musicPlaylistIds;

}

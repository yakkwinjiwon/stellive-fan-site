package com.stellive.fansite.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
@AllArgsConstructor
public enum YoutubeChannel {

    KANNA(11L, "UCKzfyYWHQ92z_2jUcSABM8Q",
            Arrays.asList()),

    KANNA_REPLAY(12L, "UC6CXZ-3mspWOXdxfpceumLw",
            Arrays.asList()),

    KANNA_MUSIC(13L, "UC6YnTqZidFg4WUiXpiCtSSQ",
            Arrays.asList("PLOPBV2KAIYnsNM0omb4ub9hofPnDHz20P",
                    "PLOPBV2KAIYnt1NJ0QCkfCqMRc00lfcjFa",
                    "PLOPBV2KAIYnthM0P8AmQ-wbBYIjkSRY4D")),

    YUNI(21L, "UClbYIn9LDbbFZ9w2shX3K0g",
            Arrays.asList("PL3HtH_xx9h_7ZGoZ9zMUQ-MPumwe21_cc",
                    "PL3HtH_xx9h_4ulddfVG8DdD_EwKUMBqvL")),

    YUNI_REPLAY(22L, "UCUj4JItmvebxPBfmGcJHsNQ",
            Arrays.asList()),

    HINA(31L, "UC1afpiIuBDcjYlmruAa0HiA",
            Arrays.asList("PLzdLDJsHzz2NiuwjyW6QgSck4PrwlSyOc")),

    HINA_REPLAY(32L, "UCnnR7k28ItzN1GAYx2rQ1rw",
            Arrays.asList()),

    MASHIRO(41L, "UC_eeSpMBz8PG4ssdBPnP07g",
            Arrays.asList("PLWwhuXFHGLvhgZZb5_rmQEMI1B0ysKJxG")),

    MASHIRO_REPLAY(42L, "UCW3wobzj-JJE_TNs7ysJPiQ",
            Arrays.asList()),

    LIZE(51L,"UC7-m6jQLinZQWIbwm9W-1iw",
            Arrays.asList("PL-DHk0WpiRNSM5oI19ImJ8sSV65mnGseX")),

    LIZE_REPLAY(52L,"UC5qsIJIixQI5k2FfkW1j1NQ",
            Arrays.asList()),

    TABI(61L,"UCAHVQ44O81aehLWfy9O6Elw",
            Arrays.asList("PLbIDsfX2JRA0oawGN209gpd_nz9IMvUlb")),

    TABI_REPLAY(62L,"UCOAD3RLV7uC3uc39ok7NLyg",
            Arrays.asList()),

    OFFICIAL(1001L, "UC2b4WRE5BZ6SIUWBeJU8rwg",
            Arrays.asList());

    private final Long id;
    private final String channelId;
    private final List<String> musicPlaylistIds;

    public static Boolean isReplay(YoutubeChannel youtubeChannel) {
        return youtubeChannel.getId() % 10 == 2;
    }

}

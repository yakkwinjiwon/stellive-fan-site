package com.stellive.fansite.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StellaYoutubeChannel implements YoutubeChannel{

    KANNA(11L, "UCKzfyYWHQ92z_2jUcSABM8Q"),
    KANNA_REPLAY(12L, "UC6CXZ-3mspWOXdxfpceumLw"),
    KANNA_MUSIC(13L, "UC6YnTqZidFg4WUiXpiCtSSQ"),
    YUNI(21L, "UClbYIn9LDbbFZ9w2shX3K0g"),
    YUNI_REPLAY(22L, "UCUj4JItmvebxPBfmGcJHsNQ"),
    HINA(31L, "UC1afpiIuBDcjYlmruAa0HiA"),
    HINA_REPLAY(32L, "UCnnR7k28ItzN1GAYx2rQ1rw"),
    MASHIRO(41L, "UC_eeSpMBz8PG4ssdBPnP07g"),
    MASHIRO_REPLAY(42L, "UCW3wobzj-JJE_TNs7ysJPiQ"),
    LIZE(51L,"UC7-m6jQLinZQWIbwm9W-1iw"),
    LIZE_REPLAY(52L,"UC5qsIJIixQI5k2FfkW1j1NQ"),
    TABI(61L,"UCAHVQ44O81aehLWfy9O6Elw"),
    TABI_REPLAY(62L,"UCOAD3RLV7uC3uc39ok7NLyg"),
    SHIBUKI(71L, "UCYxLMfeX1CbMBll9MsGlzmw"),
    SHIBUKI_REPLAY(72L, "UCfoPMrULJvdKN8GlBZJwqHg"),
    RIN(81L, "UCQmcltnre6aG9SkDRYZqFIg"),
    RIN_REPLAY(82L, "UCmw0eKk5uOv029SScRRRfqw"),
    NANA(91L, "UCcA21_PzN1EhNe7xS4MJGsQ"),
    NANA_REPLAY(92L, "UCmmzr9DL0_t5ui1TAZjv-nA"),
    RIKO(101L, "UCj0c1jUr91dTetIQP2pFeLA"),
    RIKO_REPLAY(102L, "UCVZcw2SMyL6h5CfKSCs42_A"),
    OFFICIAL(1001L, "UC2b4WRE5BZ6SIUWBeJU8rwg");

    private final Long id;
    private final String channelId;

    @Override
    public Boolean isReplay() {
        return id % 10 == 2;
    }

}

package com.stellive.fansite.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StellaChzzkChannel implements ChzzkChannel{

    KANNA(11L, "https://chzzk.naver.com/f722959d1b8e651bd56209b343932c01"),
    YUNI(21L, "https://chzzk.naver.com/45e71a76e949e16a34764deb962f9d9f"),
    HINA(31L, "https://chzzk.naver.com/b044e3a3b9259246bc92e863e7d3f3b8"),
    MASHIRO(41L, "https://chzzk.naver.com/4515b179f86b67b4981e16190817c580"),
    LIZE(51L, "https://chzzk.naver.com/4325b1d5bbc321fad3042306646e2e50"),
    TABI(61L, "https://chzzk.naver.com/a6c4ddb09cdb160478996007bff35296");

    private final Long id;
    private final String url;

}

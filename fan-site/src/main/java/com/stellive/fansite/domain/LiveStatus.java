package com.stellive.fansite.domain;

public enum LiveStatus {
    NONE,
    LIVE,
    UPCOMING;

    public static LiveStatus fromString(String status) {
        if (status == null) {
            return NONE;
        }
        return switch (status) {
            case "live" -> LIVE;
            case "upcoming" -> UPCOMING;
            default -> NONE;
        };
    }
}

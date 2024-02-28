package com.stellive.fansite.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@Getter
@Builder
@EqualsAndHashCode
public class Video {

    private String videoId;
    private Channel channel;
    private String thumbnailUrl;
    private String linkUrl;
    private String title;
    private Instant publishTime;

    @JsonProperty("id")
    public void unpackId(Map<String, Object> id) {
        this.videoId = (String) id.get(videoId);
    }

    @SuppressWarnings("unchecked")
    @JsonProperty("snippet")
    public void unpackSnippet(Map<String, Object> snippet) {
        this.title = (String) snippet.get("title");

        Map<String, Object> thumbnails = (Map<String, Object>) snippet.get("thumbnail");
        Map<String, Object> high = (Map<String, Object>) thumbnails.get("high");
        this.thumbnailUrl = (String) high.get("url");

        this.publishTime = (Instant) snippet.get("publishTime");
    }
}

package com.stellive.fansite.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@Getter
@Builder
@ToString
@EqualsAndHashCode
public class Video {

    private String videoId;
    private Channel channel;
    private String thumbnailUrl;
    private String linkUrl;
    private String title;
    private Instant publishTime;

    public Video() {
    }

    public Video(String videoId, Channel channel, String thumbnailUrl, String linkUrl, String title, Instant publishTime) {
        this.videoId = videoId;
        this.channel = channel;
        this.thumbnailUrl = thumbnailUrl;
        this.linkUrl = linkUrl;
        this.title = title;
        this.publishTime = publishTime;
    }

    @JsonProperty("id")
    public void unpackId(Map<String, Object> id) {
        this.videoId = (String) id.get(videoId);
    }

    @SuppressWarnings("unchecked")
    @JsonProperty("snippet")
    public void unpackSnippet(Map<String, Object> snippet) {
        this.title = (String) snippet.get("title");

        Map<String, Object> thumbnails = (Map<String, Object>) snippet.get("thumbnails");
        Map<String, Object> high = (Map<String, Object>) thumbnails.get("high");
        this.thumbnailUrl = (String) high.get("url");

        this.publishTime = Instant.parse((String) snippet.get("publishTime"));
    }
}

package com.stellive.fansite.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@Builder
public class VideoList {

    private List<Video> videos;

    public VideoList() {
    }

    public VideoList(List<Video> videos) {
        this.videos = videos;
    }

    @JsonProperty("items")
    private void unpackItems(List<Map<String, Object>> items) {
        this.videos = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        items.forEach(item -> {
            Video video = mapper.convertValue(item, Video.class);
            this.videos.add(video);
        });
    }

}

package com.stellive.fansite.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
public class Videos {

    private List<Video> videos;

    @JsonProperty("items")
    private void unpackItems(List<Map<String, Object>> items) {
        ObjectMapper mapper = new ObjectMapper();
        videos = new ArrayList<>();
        items.forEach(item -> {
            Video video = mapper.convertValue(item, Video.class);
            videos.add(video);
        });
    }
}

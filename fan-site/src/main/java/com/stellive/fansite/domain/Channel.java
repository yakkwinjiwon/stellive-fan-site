package com.stellive.fansite.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.util.List;
import java.util.Map;

@Entity
@Getter
@ToString
@EqualsAndHashCode
public class Channel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String externalId;
    private String thumbnailUrl;
    private String handle;

    public Channel() {

    }

    @JsonProperty("items")
    @SuppressWarnings("unchecked")
    public void unpackItems(List<Map<String, Object>> items) {

        Map<String, Object> item = items.getFirst();
        this.externalId = (String) item.get("id");

        Map<String, Object> snippet = (Map<String, Object>) item.get("snippet");
        this.handle = (String) snippet.get("customUrl");

        Map<String, Object> thumbnails = (Map<String, Object>) snippet.get("thumbnails");
        Map<String, Object> high = (Map<String, Object>) thumbnails.get("high");
        this.thumbnailUrl = (String) high.get("url");
    }
}

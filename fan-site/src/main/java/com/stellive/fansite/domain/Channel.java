package com.stellive.fansite.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Map;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "videos")
@EqualsAndHashCode
public class Channel {

    @Id
    private Long id;

    private String externalId;
    private String handle;
    private String thumbnailUrl;

    @OneToMany(mappedBy = "channel")
    private List<Video> videos;
}

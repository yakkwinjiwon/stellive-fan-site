package com.stellive.fansite.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"videos"})
@EqualsAndHashCode
public class Channel {

    @Id
    private Long id;

    private String externalId;
    private String handle;
    private String thumbnailUrl;
    private String bannerUrl;

    @OneToMany(mappedBy = "channel")
    @JsonIgnore
    private List<Video> videos;
    @OneToOne(mappedBy = "channel")
    @JsonIgnore
    private Live live;
}

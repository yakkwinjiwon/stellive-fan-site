package com.stellive.fansite.domain;

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

    @OneToMany(mappedBy = "channel")
    private List<Video> videos;

    private String externalId;
    private String handle;
    private String thumbnailUrl;
    private String bannerUrl;

}

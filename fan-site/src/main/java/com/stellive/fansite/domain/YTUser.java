package com.stellive.fansite.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "yt_user")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "videos")
@EqualsAndHashCode
public class YTUser {

    @Id
    private Long id;

    private String name;
    private String externalId;
    private String handle;
    private String thumbnailUrl;

    @OneToMany(mappedBy = "user")
    private List<YTVideo> videos;
}

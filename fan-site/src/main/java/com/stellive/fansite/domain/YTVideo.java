package com.stellive.fansite.domain;


import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "yt_video")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class YTVideo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private YTUser user;

    private String externalId;
    private Instant publishTime;
    private String title;
    private String thumbnailUrl;
}

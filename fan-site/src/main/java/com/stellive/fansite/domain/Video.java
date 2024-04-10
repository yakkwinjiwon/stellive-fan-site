package com.stellive.fansite.domain;


import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "channel_id")
    private Channel channel;

    private Long duration;
    private Integer viewCount;
    private String externalId;
    private String title;
    private String thumbnailUrl;
    private Instant publishTime;
    private Instant scheduledStartTime;
    private VideoType videoType;
    private LiveStatus liveStatus;
}

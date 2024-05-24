package com.stellive.fansite.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"channel"})
@EqualsAndHashCode
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "channel_id")
    @JsonIgnore
    private Channel channel;

    private Long duration;
    private Integer viewCount;
    private String externalId;
    private String title;
    private String thumbnailUrl;
    private Instant publishTime;
    private Instant scheduledStartTime;
    @Enumerated(EnumType.STRING)
    private VideoType videoType;
    @Enumerated(EnumType.STRING)
    private LiveStatus liveStatus;

}

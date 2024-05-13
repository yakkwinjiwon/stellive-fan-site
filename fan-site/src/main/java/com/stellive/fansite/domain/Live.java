package com.stellive.fansite.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Live {

    @Id
    private Long id;

    @OneToOne
    @JoinColumn(name = "channel_id")
    @JsonIgnore
    private Channel channel;

    private Boolean isLive;
}

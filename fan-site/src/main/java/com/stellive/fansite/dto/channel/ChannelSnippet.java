package com.stellive.fansite.dto.channel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChannelSnippet {

    private String customUrl;
    private ChannelThumbnails thumbnails;
}

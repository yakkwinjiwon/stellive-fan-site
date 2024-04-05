package com.stellive.fansite.dto.channel;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class ChannelItem {

    private String id;
    private ChannelSnippet snippet;
    private ChannelBrandingSettings brandingSettings;
}

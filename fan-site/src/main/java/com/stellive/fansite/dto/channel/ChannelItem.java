package com.stellive.fansite.dto.channel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChannelItem {

    private String id;
    private ChannelSnippet snippet;
    private ChannelBrandingSettings brandingSettings;
}

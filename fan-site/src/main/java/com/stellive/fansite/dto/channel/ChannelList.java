package com.stellive.fansite.dto.channel;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ChannelList {

    private List<ChannelItem> items;
}

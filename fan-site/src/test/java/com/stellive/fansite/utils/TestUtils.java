package com.stellive.fansite.utils;


import com.stellive.fansite.dto.channel.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.stellive.fansite.utils.TestConst.*;

public class TestUtils {

    public static ChannelList getChannelList() {
        return new ChannelList(List.of(getChannelItem()));
    }

    private static ChannelItem getChannelItem() {
        return new ChannelItem(CHANNEL_ID, getChannelSnippet(), getChannelBrandingSettings());
    }

    private static ChannelSnippet getChannelSnippet() {
        return new ChannelSnippet(CHANNEL_CUSTOM_URL, getChannelThumbnails());
    }

    private static ChannelThumbnails getChannelThumbnails() {
        return new ChannelThumbnails(getChannelThumbnail());
    }

    private static ChannelThumbnail getChannelThumbnail() {
        return new ChannelThumbnail(CHANNEL_THUMBNAIL_URL);
    }

    private static ChannelImage getChannelImage() {
        return new ChannelImage(CHANNEL_BANNER_EXTERNAL_URL);
    }

    private static ChannelBrandingSettings getChannelBrandingSettings() {
        return new ChannelBrandingSettings(getChannelImage());
    }
}

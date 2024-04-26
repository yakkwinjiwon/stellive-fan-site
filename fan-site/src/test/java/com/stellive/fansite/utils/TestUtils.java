package com.stellive.fansite.utils;


import com.stellive.fansite.dto.channel.*;
import com.stellive.fansite.dto.playlistitem.*;
import com.stellive.fansite.dto.video.*;

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

    public static PlaylistItemList getPlaylistItemListVideo(String nextPageToken) {
        return new PlaylistItemList(nextPageToken, List.of(getPlaylistItem()));
    }
    private static PlaylistItemItem getPlaylistItem() {
        return new PlaylistItemItem(getPlaylistItemSnippet());
    }
    private static PlaylistItemSnippet getPlaylistItemSnippet() {
        return new PlaylistItemSnippet(getPlaylistItemThumbnails(), getPlaylistItemResourceId());
    }
    private static PlaylistItemResourceId getPlaylistItemResourceId() {
        return new PlaylistItemResourceId(PLAYLIST_ITEM_ID);
    }
    private static PlaylistItemThumbnails getPlaylistItemThumbnails() {
        return new PlaylistItemThumbnails(getPlaylistItemThumbnail());
    }
    private static PlaylistItemThumbnail getPlaylistItemThumbnail() {
        return new PlaylistItemThumbnail(PLAYLIST_ITEM_THUMBNAIL_URL);
    }

    public static VideoList getVideoList() {
        return new VideoList(List.of(getVideoItem()));
    }
    private static VideoItem getVideoItem() {
        return new VideoItem(getVideoContentDetails(), getVideoLiveStreamingDetails(), getVideoSnippet(), getVideoStatistics());
    }
    private static VideoContentDetails getVideoContentDetails() {
        return new VideoContentDetails(VIDEO_DURATION);
    }
    private static VideoLiveStreamingDetails getVideoLiveStreamingDetails() {
        return new VideoLiveStreamingDetails(VIDEO_SCHEDULED_START_TIME);
    }
    private static VideoStatistics getVideoStatistics() {
        return new VideoStatistics(VIDEO_VIEW_COUNT);
    }
    private static VideoSnippet getVideoSnippet() {
        return new VideoSnippet(VIDEO_PUBLISHED_AT, VIDEO_CHANNEL_ID, VIDEO_TITLE, VIDEO_LIVE_BROADCAST_CONTENT, getVideoThumbnails());
    }
    private static VideoThumbnails getVideoThumbnails() {
        return new VideoThumbnails(getVideoThumbnail());
    }
    private static VideoThumbnail getVideoThumbnail() {
        return new VideoThumbnail(VIDEO_THUMBNAIL_URL);
    }

}

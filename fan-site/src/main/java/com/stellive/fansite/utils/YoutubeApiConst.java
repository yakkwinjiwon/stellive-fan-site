package com.stellive.fansite.utils;

public class YoutubeApiConst {

    /**
     * URL
     */
    public static final String URL_SEARCH = "https://www.googleapis.com/youtube/v3/search";
    public static final String URL_CHANNEL = "https://www.googleapis.com/youtube/v3/channels";
    public static final String URL_PLAYLIST_ITEMS = "https://www.googleapis.com/youtube/v3/playlistItems";
    public static final String URL_VIDEO = "https://www.googleapis.com/youtube/v3/videos";


    /**
     * Parameter key
     */
    public static final String PARAM_KEY = "key";
    public static final String PARAM_PART = "part";
    public static final String PARAM_ID = "id";
    public static final String PARAM_CHANNEL_ID = "channelId";
    public static final String PARAM_ORDER = "order";
    public static final String PARAM_TYPE = "type";
    public static final String PARAM_PLAYLIST_ID = "playlistId";
    public static final String PARAM_MAX_RESULTS = "maxResults";
    public static final String PARAM_PAGE_TOKEN = "pageToken";


    /**
     * Part value
     */
    public static final String PART_SNIPPET = "snippet";
    public static final String PART_CONTENT_DETAILS = "contentDetails";
    public static final String PART_BRANDING_SETTINGS = "brandingSettings";
    public static final String ORDER_DATE= "date";
    public static final String ORDER_RELEVANCE= "relevance";
    public static final String ORDER_VIEW_COUNT= "viewCount";

    /**
     * Type value
     */
    public static final String TYPE_CHANNEL= "channel";
    public static final String TYPE_PLAYLIST= "playlist";
    public static final String TYPE_VIDEO= "video";

    /**
     * Max Results value
     */
    public static final Integer MAX_RESULTS_VIDEO = 1;
    public static final Integer MAX_RESULTS_MUSIC = 1;
    public static final Integer MAX_RESULTS_ALL = 50;

}

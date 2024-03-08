package com.stellive.fansite.utils;

import com.stellive.fansite.domain.ChannelId;

public class YoutubeApiConst {

    /**
     * Channel Handle
     */
    public static final String CHANNEL_HANDLE_MASHIRO = "neneko_mashiro";
    public static final String CHANNEL_HANDLE_KANNA = "airikannach";
    public static final String CHANNEL_HANDLE_YUNI = "airikannach";

    /**
     * URL
     */
    public static final String URL_SEARCH = "https://www.googleapis.com/youtube/v3/search";
    public static final String URL_CHANNEL = "https://www.googleapis.com/youtube/v3/channels";


    /**
     * Search Parameter
     */
    public static final String PARAM_SEARCH_PART = "part";
    public static final String PARAM_SEARCH_CHANNEL_ID = "channelId";
    public static final String PARAM_SEARCH_ORDER = "order";
    public static final String PARAM_SEARCH_TYPE = "type";
    public static final String PARAM_SEARCH_MAX_RESULTS = "maxResults";
    public static final String PARAM_SEARCH_KEY = "key";

    /**
     * Channel Parameter
     */
    public static final String PARAM_CHANNEL_PART = "part";
    public static final String PARAM_CHANNEL_ID = "id";
    public static final String PARAM_CHANNEL_KEY = "key";

    public static final String PART_SNIPPET = "snippet";
    public static final String ORDER_DATE= "date";
    public static final String ORDER_RELEVANCE= "relevance";
    public static final String ORDER_VIEW_COUNT= "viewCount";
    public static final String TYPE_CHANNEL= "channel";
    public static final String TYPE_PLAYLIST= "playlist";
    public static final String TYPE_VIDEO= "video";


}

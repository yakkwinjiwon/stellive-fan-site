package com.stellive.fansite.utils;

import java.time.Duration;
import java.util.regex.Pattern;

public class ScraperConst {

    /**
     * Driver config
     */
    public static final String DRIVER_KEY = "webdriver.gecko.driver";
    public static final String DRIVER_PATH = "C:\\geckodriver\\geckodriver.exe";
    public static final String BROWSER_PATH = "C:\\Program Files\\Mozilla Firefox\\firefox.exe";

    public static final Duration WAIT_TIMEOUT = Duration.ofSeconds(5);

    /**
     * URL
     */
    public static final String URL_NEWS = "https://stellive.me/news";
    public static final String URL_MUSIC = "https://stellive.me/music";

    /**
     * CSS SELECTOR
     */
    public static final String CSS_SELECTOR_NEWS_MORE = ".more_btn";
    public static final String CSS_SELECTOR_NEWS_LIST = ".bh.bh_item.item";
    public static final String CSS_SELECTOR_NEWS_URL = ".bh_img_content a";
    public static final String CSS_SELECTOR_NEWS_IMG_URL = ".bh_img_content a img";
    public static final String CSS_SELECTOR_NEWS_TITLE = ".bh_title.ff-nn .title span";
    public static final String CSS_SELECTOR_NEWS_PUBLISH_TIME = ".bh_content .ff-nn";
    public static final String CSS_SELECTOR_MUSIC_LIST = ".bh_title a";
    public static final String CSS_SELECTOR_MUSIC_MORE = ".more_btn";
    public static final String CSS_SELECTOR_MUSIC_IMG = ".youtube _converted img";
    public static final String CSS_SELECTOR_LIVE_WAIT = ".channel_profile_wrapper__dU4fR";
    public static final String CSS_SELECTOR_LIVE_BADGE = ".channel_profile_badge__qVsgg";


    /**
     * Attribute
     */
    public static final String ATTRIBUTE_SRC = "src";
    public static final String ATTRIBUTE_HREF = "href";

    /**
     * Value
     */
    public static final Integer NEWS_LIMIT = 5;
    public static final Integer NEWS_ALL = 500;
    public static final Integer MUSIC_LIMIT = 5;
    public static final Integer MUSIC_ALL = 500;

    /**
     * Pattern
     */

    public static final Pattern PATTERN_MUSIC_ID = Pattern.compile("https://img.youtube.com/vi/([a-zA-Z0-9]+)/mqdefault.jpg");
}

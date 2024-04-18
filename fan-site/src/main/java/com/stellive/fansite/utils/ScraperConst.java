package com.stellive.fansite.utils;

public class ScraperConst {

    /**
     * Driver config
     */
    public static final String CHROME_DRIVER_KEY = "webdriver.chrome.driver";
    public static final String CHROME_DRIVER_PATH = "C:\\chromedriver-win32\\chromedriver.exe";

    /**
     * URL
     */
    public static final String URL_NEWS = "https://stellive.me/news";
    public static final String URL_MUSIC = "https://stellive.me/music";

    /**
     * CSS SELECTOR
     */
    public static final String CSS_SELECTOR_NEWS = "div.bh.bh_item.item";
    public static final String CSS_SELECTOR_NEWS_URL = ".bh_img_content a";
    public static final String CSS_SELECTOR_NEWS_IMG_URL = ".bh_img_content a img";
    public static final String CSS_SELECTOR_NEWS_TITLE = ".bh_title.ff-nn .title span";
    public static final String CSS_SELECTOR_NEWS_PUBLISH_TIME = ".bh_content .ff-nn";
    public static final String CSS_SELECTOR_MUSIC = ".bh.gallery_wrap.bh_row.no-gutters.hide";
    public static final String CSS_SELECTOR_MUSIC_MORE = ".more_btn.ds-f.jc-c.hide";
    public static final String CSS_SELECTOR_MUSIC_LINK = "ytp-title-link.yt-uix-sessionlink";

    /**
     * Attribute
     */
    public static final String ATTRIBUTE_SRC = "src";
    public static final String ATTRIBUTE_HREF = "href";

    /**
     * Value
     */
    public static final Integer NEWS_LIMIT = 8;
}

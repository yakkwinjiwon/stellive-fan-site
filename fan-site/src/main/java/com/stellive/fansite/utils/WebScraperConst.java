package com.stellive.fansite.utils;

public class WebScraperConst {

    public static final String CHROME_DRIVER_KEY = "webdriver.chrome.driver";
    public static final String CHROME_DRIVER_PATH = "C:\\chromedriver-win32\\chromedriver.exe";

    public static final String URL_NEWS = "https://stellive.me/news";

    /**
     * CSS SELECTOR
     */
    public static final String CSS_SELECTOR_NEWS = "div.bh.bh_item.item";
    public static final String CSS_SELECTOR_NEWS_URL = ".bh_img_content a";
    public static final String CSS_SELECTOR_NEWS_IMG_URL = ".bh_img_content a img";
    public static final String CSS_SELECTOR_NEWS_TITLE = ".bh_title.ff-nn .title span";
    public static final String CSS_SELECTOR_NEWS_PUBLISH_TIME = ".bh_content .ff-nn";

    public static final Integer NEWS_LIMIT = 8;
}

package com.stellive.fansite.utils;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.util.ObjectUtils;

import static com.stellive.fansite.utils.ScraperConst.*;

public class ScraperUtils {

    public static ChromeDriver getDriver() {
        if (ObjectUtils.isEmpty(System.getProperty(CHROME_DRIVER_KEY))) {
            System.setProperty(CHROME_DRIVER_KEY, CHROME_DRIVER_PATH);
        }

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        return new ChromeDriver(options);
    }

    public static WebDriverWait getWait(ChromeDriver driver) {
        return new WebDriverWait(driver, WAIT_TIMEOUT);
    }

}

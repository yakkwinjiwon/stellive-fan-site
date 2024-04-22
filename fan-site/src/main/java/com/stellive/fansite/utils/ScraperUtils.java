package com.stellive.fansite.utils;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

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

    public static void switchToLatestTab(ChromeDriver driver) {
        List<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.getLast());
    }
}

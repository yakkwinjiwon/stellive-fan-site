package com.stellive.fansite.utils;

import com.stellive.fansite.domain.ChzzkChannel;
import com.stellive.fansite.domain.StellaChzzkChannel;
import com.stellive.fansite.exceptions.ScraperException;
import org.openqa.selenium.JavascriptException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

import static com.stellive.fansite.utils.ScraperConst.*;

public class ScraperUtils {

    public static WebDriver getDriver() {
        if (ObjectUtils.isEmpty(System.getProperty(DRIVER_KEY))) {
            System.setProperty(DRIVER_KEY, DRIVER_PATH);
        }
        
        FirefoxOptions options = new FirefoxOptions();
        options.setBinary(BROWSER_PATH);
        options.addArguments("--headless");
        return new FirefoxDriver(options);
    }

    public static WebDriverWait getWait(WebDriver driver) {
        return new WebDriverWait(driver, WAIT_TIMEOUT);
    }

    public static void switchToLatestTab(WebDriver driver) {
        List<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.getLast());
    }

    public static void executeForEachChannel(Consumer<ChzzkChannel> channelOperation) {
        Arrays.stream(StellaChzzkChannel.values())
                .forEach(chzzkChannel ->
                        executeWithHandling(() -> channelOperation.accept(chzzkChannel))
                );
    }

    public static void executeWithHandling(Runnable task) {
        try {
            task.run();
        } catch (NoSuchElementException e) {
            throw new ScraperException("Scraper not found", e);
        } catch (TimeoutException e) {
            throw new ScraperException("Scraper time out", e);
        } catch (JavascriptException e) {
            throw new ScraperException("Scraper javascript error", e);
        } catch (Exception e) {
            throw new ScraperException("Scraper unknown error", e);
        }
    }
}

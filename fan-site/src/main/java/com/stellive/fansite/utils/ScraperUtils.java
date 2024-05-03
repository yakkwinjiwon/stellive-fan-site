package com.stellive.fansite.utils;

import com.stellive.fansite.domain.ChzzkChannel;
import com.stellive.fansite.domain.StellaChzzkChannel;
import com.stellive.fansite.domain.StellaYoutubeChannel;
import com.stellive.fansite.domain.YoutubeChannel;
import com.stellive.fansite.exceptions.ScraperException;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

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

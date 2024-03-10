package com.stellive.fansite.controller;

import com.stellive.fansite.service.TWApiService;
import com.stellive.fansite.service.YTApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.ArrayList;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/rest")
public class HomeController {

    private final YTApiService youtubeApiService;
    private final TWApiService twitterApiService;

    @GetMapping("")
    public String home() throws InterruptedException {
        log.info("home");
//        twitterApiService.test();
//        youtubeApiService.test();
        if (ObjectUtils.isEmpty(System.getProperty("webdriver.chrome.driver"))) {
            System.setProperty("webdriver.chrome.driver", "C:\\Users\\Jiwon Park\\Downloads\\chromedriver-win32\\chromedriver.exe");
        }
        ChromeOptions chromeOptions = new ChromeOptions();
        ChromeDriver driver = new ChromeDriver();

        ArrayList<WebElement> webElements = new ArrayList<>();

        if (!ObjectUtils.isEmpty(driver)) {
            driver.get("https://twitter.com/i/flow/login");
            Thread.sleep(1000);
            driver.findElement(By.xpath(
                    "//*[@id=\"layers\"]/div/div/div/div/div/div/div[2]/div[2]/div/div/div[2]/div[2]/div/div/div/div[5]/label/div/div[2]/div/input"
            )).sendKeys("haedoong009");
            Thread.sleep(1000);
            driver.findElement(By.xpath(
                    "//*[@id=\"layers\"]/div/div/div/div/div/div/div[2]/div[2]/div/div/div[2]/div[2]/div/div/div/div[6]/div"
            )).click();
            Thread.sleep(1000);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement passwordInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("" +
                    "//*[@id=\"layers\"]/div/div/div/div/div/div/div[2]/div[2]/div/div/div[2]/div[2]/div[1]/div/div/div[3]/div/label/div/div[2]/div[1]/input"
            )));
            passwordInput.sendKeys("Hinasuki009@");
            Thread.sleep(1000);
            driver.findElement(By.xpath(
                    "//*[@id=\"layers\"]/div/div/div/div/div/div/div[2]/div[2]/div/div/div[2]/div[2]/div[2]/div/div[1]/div/div/div"
            )).click();

            Thread.sleep(1000);
            driver.get("https://twitter.com/search?q=(from%3ANenekoMashiro)&src=typed_query&f=top");
            Thread.sleep(1000);

//            WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(10));
//            WebElement asdf = wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[1]/div/div/div[2]/main/div/div/div/div/div/div[3]/section/div/div/div[1]/div/div/article/div/div/div[2]/div[1]/div/div/div/div[2]/div/div[2]/div/a/div[3]/div/div[2]/div/img")));
//            List<WebElement> elements = driver.findElements(By.xpath(
//                    "//*[@id=\"react-root\"]/div/div/div[2]/main/div/div/div/div[1]/div/div[3]/section/div/div"
//            ));
//            String src = asdf.findElement(By.xpath(
//                    "/html/body/div[1]/div/div/div[2]/main/div/div/div/div/div/div[3]/section/div/div/div[1]/div/div/article/div/div/div[2]/div[1]/div/div/div/div[2]/div/div[2]/div/a/div[3]/div/div[2]/div/img"
//            )).getAttribute("src");
            String src = driver.findElement(By.xpath(
                    "/html/body/div[1]/div/div/div[2]/main/div/div/div/div/div/div[3]/section/div/div/div[1]/div/div/article/div/div/div[2]/div[1]/div/div/div/div[2]/div/div[2]/div/a/div[3]/div/div[2]/div/img"
            )).getAttribute("src");
            log.info("img src={}", src);

//            WebElement first = elements.getFirst();
//            WebElement img = first.findElement(By.xpath(
//                    "/div/div[1]/div/div/article/div/div/div[2]/div[1]/div/div/div/div[2]/div/div[2]/div/a/div[3]/div/div[2]/div/img"
//            ));
//            log.info("web element={}", img.getAttribute("src"));
        }

        if (!ObjectUtils.isEmpty(driver)) {
            driver.quit();
        }

        return "ok";
    }
}

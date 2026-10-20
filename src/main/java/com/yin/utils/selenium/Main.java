package com.yin.utils.selenium;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;

import java.io.File;

/**
 *
 *@author yin
 *@date 2025-03-25
 *@Description
 */
public class Main {
    public static void main(String[] args) {
        String chromePath = "C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe";
        ChromeDriverService driverService = new ChromeDriverService.Builder().usingDriverExecutable(new File(chromePath)).build();
        ChromeDriver chromeDriver = new ChromeDriver(driverService);
        chromeDriver.get("https://www.baidu.com");
        chromeDriver.close();
    }
}

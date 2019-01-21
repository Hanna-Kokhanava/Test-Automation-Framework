package com.linkedin.automation.core.creators.web;

import com.linkedin.automation.core.creators.IDriverCreator;
import com.linkedin.automation.core.driver.managers.web.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * Created on 21.01.2019
 */
public class WebDriverCreator implements IDriverCreator {

    @Override
    public WebDriver createDriver() {
        return createDriver(null);
    }

    @Override
    public WebDriver createDriver(DesiredCapabilities customCapabilities) {
        System.out.println("Creating driver...");
        WebDriverManager.createDriver();
        WebDriver driver = WebDriverManager.getDriver();


        return driver;
    }

    @Override
    public void closeDriver() {
        WebDriverManager.closeDriver();
    }
}

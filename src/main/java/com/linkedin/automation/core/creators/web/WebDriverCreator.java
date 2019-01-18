package com.linkedin.automation.core.creators.web;

import com.linkedin.automation.core.creators.IDriverCreator;
import com.linkedin.automation.core.driver.SupportedPlatforms;
import com.linkedin.automation.core.driver.managers.DriverManager;
import com.linkedin.automation.core.tools.files.PropertyLoader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.concurrent.TimeUnit;

public class WebDriverCreator implements IDriverCreator {
    @Override
    public WebDriver createDriver() {
        return createDriver(null);
    }

    @Override
    public WebDriver createDriver(DesiredCapabilities customCapabilities) {
        String platformName = PropertyLoader.get(PropertyLoader.BrowserProperty.BROWSER_TYPE);
        DesiredCapabilities capabilities = new DesiredCapabilities(SupportedPlatforms.valueOf(platformName).getDesiredCapabilities());
        //TODO another capabilities will be useful when customer wrapper Browser will be created
        WebDriver driver;

        switch (platformName) {
            case BrowserType.CHROME:
                //TODO set path to executable driver - xml config?
                driver = new ChromeDriver(SupportedPlatforms.valueOf(platformName).getChromeOptions());
            default:
                //TODO add another browsers
                driver = null;
        }

        //TODO ignore NPE for a while
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        return driver;
    }

    @Override
    public void closeDriver() {
        DriverManager.closeDriver();
    }
}

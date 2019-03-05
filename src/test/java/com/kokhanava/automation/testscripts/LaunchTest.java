package com.kokhanava.automation.testscripts;

import com.kokhanava.automation.core.driver.managers.web.WebDriverManager;
import com.kokhanava.automation.core.logger.Logger;
import com.kokhanava.automation.services.web.navigation.Navigation;
import com.kokhanava.automation.unit.BaseCase;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * Created on 10.03.2018
 */
public class LaunchTest extends BaseCase {

    @Test(description = "Go to base page, refresh and check title")
    public void openBasePage() {
        Logger.info("Test to check base configuration was launched");
        String actualPageTitle = Navigation.openPage(WebDriverManager.getDriver(), "https://github.com/");
        String expectedPageTitle = "The world’s leading software development platform · GitHub";
        Navigation.refresh(WebDriverManager.getDriver());
        assertEquals(actualPageTitle, expectedPageTitle);
    }
}

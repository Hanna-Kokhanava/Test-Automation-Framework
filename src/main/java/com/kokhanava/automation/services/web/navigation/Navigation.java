package com.kokhanava.automation.services.web.navigation;

import org.openqa.selenium.WebDriver;

/**
 * Created on 05.03.2019
 */
public class Navigation {

    /**
     * Opens web page
     *
     * @param driver {@link WebDriver} instance
     * @param url    page url
     * @return current page title
     */
    public static String openPage(WebDriver driver, String url) {
        driver.navigate().to(url);
        return driver.getTitle();
    }

    /**
     * Moves back in the browser's history
     *
     * @param driver {@link WebDriver} instance
     * @return current page title
     */
    public static String goBack(WebDriver driver) {
        driver.navigate().back();
        return driver.getTitle();
    }

    /**
     * Moves forward in the browser's history
     *
     * @param driver {@link WebDriver} instance
     * @return current page title
     */
    public static String goForward(WebDriver driver) {
        driver.navigate().forward();
        return driver.getTitle();
    }

    /**
     * Refreshes current page
     *
     * @param driver {@link WebDriver} instance
     * @return current page title
     */
    public static String refresh(WebDriver driver) {
        driver.navigate().refresh();
        return driver.getTitle();
    }
}

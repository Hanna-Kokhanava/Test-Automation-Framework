package com.linkedin.automation.core.browser;

import com.linkedin.automation.core.tools.files.ProjectDir;
import com.linkedin.automation.core.tools.files.PropertyLoader;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.Objects;

/**
 * Created on 21.01.2019
 */
public class BrowserManager {

    @XmlRootElement
    private static class Browsers {
        @XmlElement(name = "browser")
        private List<Browser> browserList;
    }

    private static final List<Browser> actualBrowsersList = ProjectDir.readFromResource(Browsers.class,
            PropertyLoader.get(PropertyLoader.BrowserProperty.BROWSERS_XML)).browserList;

    private static final ThreadLocal<Browser> currentBrowser = new ThreadLocal<>();

    /**
     * Returns current browser instance
     *
     * @return {@link Browser}
     */
    public static Browser getCurrentBrowser() {
        if (Objects.isNull(currentBrowser.get())) {
            String browserName = PropertyLoader.get(PropertyLoader.BrowserProperty.BROWSER_TYPE, "");
            if (!browserName.equals(""))
                setCurrentBrowser(getBrowser(browserName));
        }
        return currentBrowser.get();
    }

    /**
     * Set {@link Browser) instance as current browser
     *
     * @param Browser instance
     */
    public static void setCurrentBrowser(Browser browser) {
        currentBrowser.remove();
        currentBrowser.set(browser);
        System.out.println("Set current browser: " + browser);
    }

    public static Browser getBrowser(String browserName) {
        for (Browser browser : actualBrowsersList) {
            if (browser.getBrowserName().equalsIgnoreCase(browserName)) {
                return browser;
            }
        }
        throw new RuntimeException("Browser with name [" + browserName + "] was not found");
    }
}

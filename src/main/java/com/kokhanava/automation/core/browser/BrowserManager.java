package com.kokhanava.automation.core.browser;

import com.kokhanava.automation.core.logger.Logger;
import com.kokhanava.automation.core.tools.files.ProjectDir;
import com.kokhanava.automation.core.tools.files.property.PropertyLoader;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.Objects;

/**
 * Manager of the {@link Browser} instances
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
            if (!browserName.isEmpty()) {
                setCurrentBrowser(getBrowserByName(browserName));
            }
        }
        return currentBrowser.get();
    }

    /**
     * Set {@link Browser) instance as current browser
     *
     * @param Browser instance
     */
    public static void setCurrentBrowser(Browser browser) {
        Logger.debug("Set current Browser instance name [" + browser.getBrowserName() + "]");
        currentBrowser.remove();
        currentBrowser.set(browser);
    }

    /**
     * Returns {@link Browser} by its name
     *
     * @param browserName name of the browser
     * @return {@link Browser} instance
     */
    public static Browser getBrowserByName(String browserName) {
        return actualBrowsersList.stream()
                .filter(browser -> browser.getBrowserName().equalsIgnoreCase(browserName))
                .findFirst()
                .orElseThrow(() -> new NullPointerException("Browser with name [" + browserName + "] was not found"));
    }
}

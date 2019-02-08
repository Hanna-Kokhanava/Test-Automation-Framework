package com.kokhanava.automation.core.driver.dependencies;

import com.kokhanava.automation.core.browser.Browser;
import com.kokhanava.automation.core.browser.BrowserManager;
import com.kokhanava.automation.core.logger.Logger;
import com.kokhanava.automation.core.tools.files.property.PropertyLoader;

public class WebDependencies implements IDependencies {

    /**
     * Sets current browser
     */
    @Override
    public void configureDependencies() {
        Logger.debug("Configure WebDriver dependencies");
        Browser browser = BrowserManager.getBrowserByName(PropertyLoader.get(PropertyLoader.BrowserProperty.BROWSER_TYPE));
        BrowserManager.setCurrentBrowser(browser);
    }
}

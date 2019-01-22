package com.linkedin.automation.core.driver.dependencies;

import com.linkedin.automation.core.browser.Browser;
import com.linkedin.automation.core.browser.BrowserManager;
import com.linkedin.automation.core.tools.files.PropertyLoader;

public class WebDependencies implements IDependencies {

    @Override
    public void configureDependencies() {
        Browser browser = BrowserManager.getBrowser(PropertyLoader.get(PropertyLoader.BrowserProperty.BROWSER_TYPE));
        BrowserManager.setCurrentBrowser(browser);
    }
}

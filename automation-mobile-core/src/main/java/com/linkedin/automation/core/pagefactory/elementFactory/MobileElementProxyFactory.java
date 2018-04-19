package com.linkedin.automation.core.pagefactory.elementFactory;

import com.linkedin.automation.core.pagefactory.elementFactory.proxyhandlers.locatorhandlers.ICacheableLocatorHandler;
import com.linkedin.automation.core.pagefactory.elementFactory.proxyhandlers.locatorhandlers.LocatorHandler;
import io.appium.java_client.pagefactory.locator.CacheableLocator;

/**
 * Contains factory methods for creating lazy proxies
 */
public class MobileElementProxyFactory extends AbstractMobileElementProxyFactory {

    protected ICacheableLocatorHandler createLocatorHandler(CacheableLocator locator, String name) {
        return new LocatorHandler(locator, name);
    }

}

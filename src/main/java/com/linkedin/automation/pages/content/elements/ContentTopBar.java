package com.linkedin.automation.pages.content.elements;

import com.linkedin.automation.core.device.DeviceManager;
import com.linkedin.automation.core.logger.Logger;
import com.linkedin.automation.core.matchers.WrapsMatchers;
import com.linkedin.automation.pages.TopBar;
import com.linkedin.automation.pages.resources.NavigationItems;
import org.openqa.selenium.WebElement;

public class ContentTopBar extends TopBar {
    {
        availableTypeList.add(NavigationItems.BarItem.SEARCH);
        availableTypeList.add(NavigationItems.BarItem.HOME_APP);
    }

    public ContentTopBar(WebElement element) {
        super(element);
    }

    @Override
    public boolean isCorrect() {
        String osName = DeviceManager.getCurrentDevice().getDeviceType().os().name();
        Logger.info(String.format("Check that %s Content TopBar panel loaded is correct", osName));

        String strMatcher = String.format("%s Content TopBar correct", osName);
        return WrapsMatchers.isEqual(strMatcher, super.isCorrect() ? strMatcher : "not correct").check();
    }
}

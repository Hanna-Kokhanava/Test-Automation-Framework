package com.linkedin.automation.services.ui.content;

import com.linkedin.automation.controls.Button;
import com.linkedin.automation.core.matchers.WrapsMatchers;
import com.linkedin.automation.pages.content.elements.AbstractMainBar;
import com.linkedin.automation.pages.resources.NavigationItems;
import com.linkedin.automation.services.ui.handlers.AbstractBarHandler;

import java.util.Objects;

public class NavigationBarHandler extends AbstractBarHandler<NavigationItems.BarItem, AbstractMainBar> {
    private final String actionMatcherString = "%s bar action performed";
    /**
     * Checks that for Android it is visible and expanded
     *
     * @return is expanded
     */
    public boolean isBarExpanded() {
        return isAvailableType(NavigationItems.BarItem.MENU) &&
                isBarPresents() &&
                !Objects.requireNonNull(getCheckedBar().getBarItem(NavigationItems.BarItem.MENU), "MENU item did not find").isDisplayed();
    }

    /**
     * Checks that for Android and iOS it is visible
     *
     * @return is presents
     */
    public boolean isBarPresents() {
        return getCheckedBar().isDisplayed();
    }

    public boolean isItemActive(NavigationItems.BarItem item) {
        Button examComponent = getCheckedBar().getBarItem(item);
        Button activeComponent = getCheckedBar().getActiveBarItem();

        return null != examComponent && examComponent.getWrappedElement().getLocation().equals(activeComponent.getWrappedElement().getLocation());
    }

    @Override
    public boolean itemActionPerform(NavigationItems.BarItem item) {
        String matcherString = String.format(actionMatcherString, item);
        boolean isSuccessful = isAvailableType(item) && super.itemActionPerform(item);
        WrapsMatchers.isEqual(matcherString, isSuccessful ? matcherString : "false").check();
        return isSuccessful;
    }

    public boolean isNavigationBarItemsPresent(NavigationItems.BarItem... barItems) {
        boolean isAllPresents = true;
        for (NavigationItems.BarItem barItem : barItems) {
            isAllPresents = getCheckedBar().getDisplayedMatcher(barItem).check() && isAllPresents;
        }
        return isAllPresents;
    }

}

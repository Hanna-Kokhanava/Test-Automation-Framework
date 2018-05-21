package com.linkedin.automation.pages.content.elements;

import com.linkedin.automation.controls.Button;
import com.linkedin.automation.core.matchers.CommonMatchers;
import com.linkedin.automation.pages.resources.NavigationItems;
import org.openqa.selenium.WebElement;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;


/**
 * Class provides bar of elements that provides navigation to target content and some other elements
 */
public abstract class AbstractMainBar extends AbstractNavigationBar<Button, NavigationItems.BarItem> {

    protected List<NavigationItems.BarItem> availableTypeList = new ArrayList<>();

    {
        availableTypeList.add(NavigationItems.BarItem.HOME);
        availableTypeList.add(NavigationItems.BarItem.NETWORK);
        availableTypeList.add(NavigationItems.BarItem.MESSAGING);
        availableTypeList.add(NavigationItems.BarItem.NOTIFICATIONS);
        availableTypeList.add(NavigationItems.BarItem.JOBS);
    }

    protected AbstractMainBar(WebElement element) {
        super(element);
    }

    @Nonnull
    @Override
    public List<NavigationItems.BarItem> getAvailableTypes() {
        return availableTypeList;
    }

    /**
     * Returns selected bar item
     *
     * @return selected bar item
     */
    @Nonnull
    public abstract Button getActiveBarItem();

    public boolean isCorrect() {
        return CommonMatchers.allOf(
                getDisplayedMatcher(NavigationItems.BarItem.HOME),
                getDisplayedMatcher(NavigationItems.BarItem.NETWORK),
                getDisplayedMatcher(NavigationItems.BarItem.MESSAGING),
                getDisplayedMatcher(NavigationItems.BarItem.NOTIFICATIONS),
                getDisplayedMatcher(NavigationItems.BarItem.JOBS)
        ).check();
    }

}

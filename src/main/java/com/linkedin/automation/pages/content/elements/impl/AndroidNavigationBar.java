package com.linkedin.automation.pages.content.elements.impl;

import com.linkedin.automation.controls.Button;
import com.linkedin.automation.core.logger.Logger;
import com.linkedin.automation.pages.content.elements.AbstractMainBar;
import com.linkedin.automation.pages.resources.NavigationItems;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

public class AndroidNavigationBar extends AbstractMainBar {

    @AndroidFindBy(xpath = "//android.widget.HorizontalScrollView[contains(@resource-id, 'home_tab_strip')]/android.support.v7.app.ActionBar$Tab")
    private List<Button> contentNavigationButtonList;

    @AndroidFindBy(xpath = "///android.widget.HorizontalScrollView[contains(@resource-id, 'home_tab_strip')]/android.support.v7.app.ActionBar$Tab[@selected='true']")
    private Button activeButton;

    public AndroidNavigationBar(WebElement element) {
        super(element);
    }

    protected List<Button> getContentNavigationButtonList() {
        return contentNavigationButtonList;
    }

    @Nullable
    @Override
    public Button getBarItem(@Nonnull NavigationItems.BarItem item) {
        return getContentNavigationButtonList()
                .stream()
                .filter(itemInMenu -> isComplianceWith(itemInMenu, item))
                .findFirst()
                .orElse(null);
    }

    @Nonnull
    @Override
    public List<Button> getBarItemList() {
        return contentNavigationButtonList;
    }

    @Nonnull
    @Override
    public Button getActiveBarItem() {
        return activeButton;
    }

    @Nonnull
    @Override
    public NavigationItems.BarItem getActiveBarItemType() {
        NavigationItems.BarItem targetItemType = getAvailableTypes()
                .stream()
                .filter(type -> isComplianceWith(getActiveBarItem(), type))
                .findFirst()
                .orElse(null);
        return Objects.requireNonNull(targetItemType, "Not defined type of the Active item on the Android navigation bar");
    }

    @Override
    public boolean isCorrect() {
        Logger.info("Checks Navigation Bar for Android device loaded is correct");
        return super.isCorrect();
    }

    /**
     * Checks the compliance with bar component and Bar Item
     *
     * @param itemComponent component of of Navigation Bar
     * @param barItem       target item
     * @return is component compliance with target item
     */
    private boolean isComplianceWith(@Nonnull Button itemComponent, @Nonnull NavigationItems.BarItem barItem) {
        String nameAttribute = itemComponent.getWrappedElement().getAttribute("contentDescription");
        return nameAttribute.toLowerCase().startsWith(barItem.getName().toLowerCase());
    }

}

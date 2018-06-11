package com.linkedin.automation.pages;

import com.linkedin.automation.controls.Button;
import com.linkedin.automation.page_elements.block.MobileBlock;
import com.linkedin.automation.pages.resources.NavigationItems;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public abstract class TopBar extends MobileBlock implements IBar<Button, NavigationItems.BarItem> {

    @AndroidFindBy(id = "home_app_launcher")
    private Button homeButton;

    @AndroidFindBy(id = "me_launcher")
    private Button profileButton;

    @AndroidFindBy(id = "search_bar")
    private Button searchButton;

    private List<Button> barItemList;
    protected List<NavigationItems.BarItem> availableTypeList = new ArrayList<>();

    public TopBar(WebElement element) {
        super(element);
    }

    protected Button getHomeButton() {
        return homeButton;
    }

    protected Button getSearchButton() {
        return searchButton;
    }

    public Button getProfileButton() {
        return profileButton;
    }

    @Nullable
    @Override
    public Button getBarItem(NavigationItems.BarItem item) {
        Button targetButton;
        switch (item) {
            case HOME_APP:
                targetButton = getHomeButton();
                break;
            case PROFILE:
                targetButton = getProfileButton();
                break;
            case SEARCH:
                targetButton = getSearchButton();
                break;
            default:
                targetButton = null;
        }
        return targetButton;
    }

    @Nonnull
    @Override
    public List<Button> getBarItemList() {
        barItemList.clear();
        Button itemComponent;
        for (NavigationItems.BarItem barItem : getAvailableTypes()) {
            if (null != (itemComponent = getBarItem(barItem))) {
                barItemList.add(itemComponent);
            }
        }
        return barItemList;
    }

    @Nonnull
    @Override
    public List<NavigationItems.BarItem> getAvailableTypes() {
        return availableTypeList;
    }

    @Override
    public boolean isCorrect() {
//        boolean isCorrect = CommonMatchers.anyOf(
//                SelfElementMatchers.displayed(getTitleLabel()),
//                SelfElementMatchers.displayed(getBrandLogoImage())
//        ).check();
//
//        return isCorrect && isBarItemsLoadedCorrect();
        return true;
    }

    protected boolean isBarItemsLoadedCorrect() {
//        boolean isCorrect = true;
//        for (LgButton itemButton : getBarItemList()) {
//            isCorrect = isCorrect &&
//                    WhenSelfMatchers.when(Device.DeviceType.ANDROID,
//                            SelfElementMatchers.displayed(itemButton),
//                            SelfElementMatchers.exists(itemButton)
//                    ).check();
//        }
//        return isCorrect;
        return true;
    }
}

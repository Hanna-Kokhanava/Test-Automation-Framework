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

    @AndroidFindBy(id = "toolbar_menu_image_view")
    private Button menuButton;

    @AndroidFindBy(id = "toolbar_back_image_view")
    private Button backButton;

    @AndroidFindBy(id = "toolbar_search_image_view")
    private Button searchButton;

    private List<Button> barItemList;
    protected List<NavigationItems.BarItem> availableTypeList = new ArrayList<>();

    public TopBar(WebElement element) {
        super(element);
    }

    protected Button getMenuButton() {
        return menuButton;
    }

    public Button getBackButton() {
        return backButton;
    }

    protected Button getSearchButton() {
        return searchButton;
    }

    @Nullable
    @Override
    public Button getBarItem(NavigationItems.BarItem item) {
        Button targetButton;
        switch (item) {
            case MENU:
                targetButton = getMenuButton();
                break;
            case BACK:
                targetButton = getBackButton();
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

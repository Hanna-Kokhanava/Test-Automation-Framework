package com.linkedin.automation.pages;

import com.linkedin.automation.page_elements.element.IButton;
import com.linkedin.automation.pages.resources.NavigationItem;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public interface IBar<BarItemComponent extends IButton, BarItemType extends NavigationItem> {

    @Nullable
    BarItemComponent getBarItem(BarItemType item);

    @Nonnull
    List<BarItemComponent> getBarItemList();

    @Nonnull
    List<BarItemType> getAvailableTypes();
}

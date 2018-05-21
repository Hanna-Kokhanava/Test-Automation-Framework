package com.linkedin.automation.services.ui.handlers;

import com.linkedin.automation.controls.Button;
import com.linkedin.automation.pages.IBar;
import com.linkedin.automation.pages.resources.NavigationItem;
import com.linkedin.automation.services.ui.PageElementProvider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public abstract class AbstractBarHandler<
        NavigationItemType extends NavigationItem,
        BarType extends IBar<Button, NavigationItemType>
        > {

    private PageElementProvider<BarType> barProvider;
    private Set<NavigationItemType> disabledTypes = new HashSet<>();

    public AbstractBarHandler() {
        this((BarType) null); // for Guice
    }

    public AbstractBarHandler(BarType bar) {
        setBar(bar);
    }

    public AbstractBarHandler(PageElementProvider<BarType> barProvider) {
        setBar(barProvider);
    }

    @Nullable
    public BarType getBar() {
        return Objects.isNull(barProvider) ? null : barProvider.getPageElement();
    }

    public void setBar(@Nullable BarType bar) {
        if (null != barProvider && bar == barProvider.getPageElement()) {
            return;
        }
        barProvider = new PageElementProvider<BarType>() {
            BarType element = bar;

            @Override
            public BarType getPageElement() {
                return element;
            }
        };
    }

    public void setBar(@Nullable PageElementProvider<BarType> barProvider) {
        this.barProvider = barProvider;
    }

    @Nonnull
    public BarType getCheckedBar() {
        return Objects.requireNonNull(getBar(), "Not defined Bar instance");
    }

    /**
     * Executes click on bar item
     *
     * @param item type target bar item
     * @return action executed success
     */
    public boolean itemActionPerform(NavigationItemType item) {
        Button targetItemComponent;
        if (null != (targetItemComponent = getCheckedBar().getBarItem(item))) {
            targetItemComponent.click();
            return true;
        }
        return false;
    }

    /**
     * Temporarily disable any actions for some available bar item type.
     *
     * @param type available bar item type
     * @return <tt>true</tt> if this bar item type was not already disabled
     */
    public boolean disableType(NavigationItemType type) {
        return disabledTypes.add(type);

    }

    /**
     * Enables some bar item type that was disabled previously.
     *
     * @param type disabled bar item type
     */
    public boolean enableType(NavigationItemType type) {
        return disabledTypes.remove(type);
    }

    /**
     * Checks that specific bar item type was not disabled.
     *
     * @param type specific bar item type
     * @return <tt>true</tt> if this bar item type was not disabled
     */
    public boolean isEnabledType(NavigationItemType type) {
        return !disabledTypes.contains(type);
    }

    public boolean isAvailableType(NavigationItemType item) {
        return getCheckedBar().getAvailableTypes().contains(item);
    }

}

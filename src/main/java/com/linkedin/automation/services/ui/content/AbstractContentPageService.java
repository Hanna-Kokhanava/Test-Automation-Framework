package com.linkedin.automation.services.ui.content;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.linkedin.automation.core.device.DeviceManager;
import com.linkedin.automation.core.logger.Logger;
import com.linkedin.automation.core.matchers.CommonMatchers;
import com.linkedin.automation.core.matchers.SelfElementMatchers;
import com.linkedin.automation.page_elements.handlers.DefaultWaiter;
import com.linkedin.automation.pages.TopBar;
import com.linkedin.automation.pages.content.AbstractContentPage;
import com.linkedin.automation.pages.content.elements.AbstractContentBlock;
import com.linkedin.automation.pages.resources.NavigationItems;
import com.linkedin.automation.services.ui.PageProvider;
import com.linkedin.automation.services.ui.handlers.AbstractBarHandler;
import com.linkedin.automation.services.ui.handlers.AbstractBlockHandler;
import com.linkedin.automation.services.ui.handlers.IPageVerifier;
import org.openqa.selenium.support.ui.Clock;
import org.openqa.selenium.support.ui.SystemClock;

import java.util.function.Function;

public abstract class AbstractContentPageService<
        TopBarType extends TopBar,
        TopBarHandlerType extends AbstractBarHandler<NavigationItems.BarItem, TopBarType>,
        ContentBlockType extends AbstractContentBlock,
        ContentPageType extends AbstractContentPage<TopBarType, ContentBlockType>,
        ContentHandlerType extends AbstractBlockHandler<ContentBlockType>>
        extends PageProvider<ContentPageType>
        implements IPageVerifier {

    @Inject
    private Provider<TopBarHandlerType> topBarHandlerProvider;
    @Inject
    private Provider<NavigationBarHandler> navigationBarHandlerProvider;
    @Inject
    private Provider<ContentHandlerType> contentHandlerProvider;

    protected ContentHandlerType getContentHandler() {
        ContentHandlerType contentHandler = contentHandlerProvider.get();
        contentHandler.setBlock(getPage().getContentBlock());
        return contentHandler;
    }

    @Override
    protected ContentPageType getPage() {
        return super.getPage();
    }

    public TopBarHandlerType getTopBarHandler() {
        TopBarHandlerType topBarHandler = topBarHandlerProvider.get();
        topBarHandler.setBar(getPage().getTopBar());
        return topBarHandler;
    }

    public NavigationBarHandler getNavigationBarHandler() {
        NavigationBarHandler navigationBarHandler = navigationBarHandlerProvider.get();
        navigationBarHandler.setBar(getPage().getMainNavigationBar());
        return navigationBarHandler;
    }

    @Override
    public boolean isPageLoaded() {
        Logger.info("Check that {} page is loaded:", getPage().getName().toUpperCase());
        return CommonMatchers.allOf(
                SelfElementMatchers.displayed(getPage().getTopBar()),
                SelfElementMatchers.displayed(getPage().getContentBlock())
        ).check();
    }

    /**
     * Select Bar item by click on target button
     *
     * @param barItem Bar item
     */
    public void goTo(NavigationItems.BarItem barItem) {
        if (getTopBarHandler().itemActionPerform(barItem)) {
            return;
        }

        if (!getNavigationBarHandler().isBarPresents()) {
            goTo(NavigationItems.BarItem.HOME_APP);// it is invoke for Android Phone only
        }

        if (NavigationItems.BarItem.HOME_APP == barItem) {
            return;
        }

        if (!getNavigationBarHandler().itemActionPerform(barItem)) {
            throw new RuntimeException(String.format("Cannot perform action over the Bar Item component '%s'", barItem.getName()));
        }
    }

    private void handleNavigationMenu(Function<Object, Boolean> checker) {
        if (!DeviceManager.getCurrentDevice().isAndroid()) {
            return;
        }

        Clock clock = new SystemClock();
        long end = clock.laterBy(DefaultWaiter.DEFAULT_TIMEOUT.toMillis());
        while (checker.apply(null) && clock.isNowBefore(end)) {
            goTo(NavigationItems.BarItem.HOME_APP);
        }
    }

    @Override
    public boolean isPageLoadedCorrect() {
        Logger.info("Check that {} page contains all important components:", getPage().getName().toUpperCase());
        return getPage().getTopBar().isCorrect() &&
                getPage().getContentBlock().isCorrect();
    }
}

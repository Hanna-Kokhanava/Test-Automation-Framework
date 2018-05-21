package com.linkedin.automation.pages.content;

import com.linkedin.automation.pages.DefaultMobilePage;
import com.linkedin.automation.pages.TopBar;
import com.linkedin.automation.pages.content.elements.AbstractContentBlock;
import com.linkedin.automation.pages.content.elements.AbstractMainBar;
import com.linkedin.automation.pages.content.elements.impl.AndroidNavigationBar;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.OverrideWidget;

public abstract class AbstractContentPage<TopBarType extends TopBar, ContentBlockType extends AbstractContentBlock> extends DefaultMobilePage {

    @OverrideWidget(androidUIAutomator = AndroidNavigationBar.class)
    @AndroidFindBy(id = "home_tab_strip")
    private AbstractMainBar mainNavigationBar;

    public abstract TopBarType getTopBar();

    public AbstractMainBar getMainNavigationBar() {
        return mainNavigationBar;
    }

    public abstract ContentBlockType getContentBlock();

    /**
     * Returns name of current page as string
     *
     * @return name of page
     */
    public abstract String getName();

}

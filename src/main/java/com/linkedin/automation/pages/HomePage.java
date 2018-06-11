package com.linkedin.automation.pages;

import com.linkedin.automation.pages.content.AbstractContentPage;
import com.linkedin.automation.pages.content.elements.ContentTopBar;
import com.linkedin.automation.pages.content.elements.impl.home.HomeContentBlock;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class HomePage extends AbstractContentPage<ContentTopBar, HomeContentBlock> {

    @AndroidFindBy(id = "home_view_pager")
    private HomeContentBlock homeContentBlock;

    @AndroidFindBy(id = "home_top_bar")
    private ContentTopBar topBar;

    @Override
    public ContentTopBar getTopBar() {
        return topBar;
    }

    @Override
    public HomeContentBlock getContentBlock() {
        return homeContentBlock;
    }

    @Override
    public String getName() {
        return null;
    }
}

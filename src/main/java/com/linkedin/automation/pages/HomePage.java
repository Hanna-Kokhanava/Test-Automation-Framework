package com.linkedin.automation.pages;

import com.linkedin.automation.pages.content.AbstractContentPage;
import com.linkedin.automation.pages.content.elements.ContentTopBar;
import com.linkedin.automation.pages.content.elements.impl.home.HomeContentBlock;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class HomePage extends AbstractContentPage<ContentTopBar, HomeContentBlock> {

    @AndroidFindBy(id = "growth_prereg_fragment_view_pager")
    private HomeContentBlock homeContentBlock;

    public HomeContentBlock getHomeBlock() {
        return homeContentBlock;
    }

    @Override
    public ContentTopBar getTopBar() {
        return null;
    }

    @Override
    public HomeContentBlock getContentBlock() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }
}

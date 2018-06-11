package com.linkedin.automation.pages.content.elements.impl.home;

import com.linkedin.automation.pages.content.elements.AbstractContentBlock;
import com.linkedin.automation.services.ui.content.impl.home.elements.FeedItem;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

public class HomeContentBlock extends AbstractContentBlock {

    @AndroidFindBy(id = "feed_item_update_card")
    private FeedItem firstFeedItem;

    public HomeContentBlock(WebElement element) {
        super(element);
    }

    @Override
    public boolean isCorrect() {
        return firstFeedItem.isDisplayed()
                && firstFeedItem.isCorrect();
    }

    public FeedItem getFirstFeedItem() {
        return firstFeedItem;
    }
}

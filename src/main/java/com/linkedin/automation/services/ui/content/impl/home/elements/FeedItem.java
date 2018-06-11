package com.linkedin.automation.services.ui.content.impl.home.elements;

import com.linkedin.automation.controls.Button;
import com.linkedin.automation.controls.TextField;
import com.linkedin.automation.page_elements.block.MobileBlock;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

public class FeedItem extends MobileBlock {

    @AndroidFindBy(id = "feed_render_item_actor_name")
    private TextField actorName;

    @AndroidFindBy(id = "feed_render_item_actor_headline")
    private TextField actorHeadline;

    @AndroidFindBy(id = "feed_render_item_text")
    private TextField feedText;

    @AndroidFindBy(id = "feed_render_item_social_actions_like")
    private Button likeButton;

    @AndroidFindBy(id = "feed_render_item_social_actions_comment")
    private Button commentButton;

    @AndroidFindBy(id = "feed_render_item_social_actions_reshare")
    private Button shareButton;

    public FeedItem(WebElement element) {
        super(element);
    }

    @Override
    public boolean isCorrect() {
        return actorName.isDisplayed()
                && actorHeadline.isDisplayed()
                && feedText.isDisplayed();
    }

    public TextField getActorName() {
        return actorName;
    }

    public TextField getActorHeadline() {
        return actorHeadline;
    }

    public TextField getFeedText() {
        return feedText;
    }

    public Button getLikeButton() {
        return likeButton;
    }

    public Button getCommentButton() {
        return commentButton;
    }

    public Button getShareButton() {
        return shareButton;
    }
}

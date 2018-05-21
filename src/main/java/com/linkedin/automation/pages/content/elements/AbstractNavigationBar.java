package com.linkedin.automation.pages.content.elements;

import com.linkedin.automation.controls.AbstractScrollableBlock;
import com.linkedin.automation.controls.Button;
import com.linkedin.automation.core.matchers.SelfMatcher;
import com.linkedin.automation.core.matchers.WrapsMatchers;
import com.linkedin.automation.page_elements.element.AbstractButton;
import com.linkedin.automation.pages.IBar;
import com.linkedin.automation.pages.resources.NavigationItem;
import org.openqa.selenium.WebElement;

import javax.annotation.Nonnull;


/**
 * Class provides bar of elements that provides navigation to target content and some other elements
 */
public abstract class AbstractNavigationBar
        <ComponentItemType extends AbstractButton, ItemType extends NavigationItem>
        extends AbstractScrollableBlock
        implements IBar<ComponentItemType, ItemType> {

    public AbstractNavigationBar(WebElement element) {
        super(element);
    }

    /**
     * Returns selected bar item
     *
     * @return selected bar item
     */
    @Nonnull
    public abstract Button getActiveBarItem();

    /**
     * Returns type of selected bar item
     *
     * @return type of selected bar item
     */
    public abstract ItemType getActiveBarItemType();

    public SelfMatcher getDisplayedMatcher(ItemType item) {
        String trueAssertion = String.format("%s displayed", item.getName().toUpperCase());
        String falseAssertion = "not %s";

        ComponentItemType itemComponent = getBarItem(item);

        boolean assertValidation = false;
        if (null == itemComponent) {
            falseAssertion = String.format(falseAssertion, "found");
        } else if (!itemComponent.isDisplayed()) {
            falseAssertion = String.format(falseAssertion, "displayed");
        } else {
            assertValidation = true;
        }

        return WrapsMatchers.isEqual(trueAssertion, assertValidation ? trueAssertion : falseAssertion);
    }

}

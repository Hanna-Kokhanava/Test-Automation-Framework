package com.linkedin.automation.pages.content.elements;

import com.linkedin.automation.page_elements.block.MobileBlock;
import org.openqa.selenium.WebElement;

public abstract class AbstractContentBlock extends MobileBlock {

    public AbstractContentBlock(WebElement element) {
        super(element);
    }
}

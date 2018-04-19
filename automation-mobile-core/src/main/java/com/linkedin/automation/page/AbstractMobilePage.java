package com.linkedin.automation.page;

import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.FieldDecorator;

public class AbstractMobilePage {

    private FieldDecorator fieldDecorator;

    protected AbstractMobilePage(SearchContext searchContext) {
        this(new AppiumFieldDecorator(searchContext));
    }

    protected AbstractMobilePage(FieldDecorator fieldDecorator) {
        setFieldDecorator(fieldDecorator);
    }

    protected void setFieldDecorator(FieldDecorator fieldDecorator) {
        this.fieldDecorator = fieldDecorator;
        revalidatePage();
    }

    /**
     * Init or reinit all elements on this page.
     * Cached elements will updated also
     */
    protected void revalidatePage() {
        PageFactory.initElements(fieldDecorator, this);
    }

}

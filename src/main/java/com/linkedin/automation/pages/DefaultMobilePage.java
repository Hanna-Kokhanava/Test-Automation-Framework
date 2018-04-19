package com.linkedin.automation.pages;

import com.linkedin.automation.core.device.DeviceManager;
import com.linkedin.automation.core.driver.managers.DriverManager;
import com.linkedin.automation.core.pagefactory.decorator.MobileElementDecorator;
import com.linkedin.automation.page.AbstractMobilePage;
import io.appium.java_client.pagefactory.TimeOutDuration;
import org.openqa.selenium.support.pagefactory.FieldDecorator;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class DefaultMobilePage extends AbstractMobilePage {

    protected static final Duration timeOutDuration = DeviceManager.getCurrentDevice().isAndroid()
            ? Duration.ofSeconds(1)
            : Duration.ofSeconds(3);
    protected static final TimeOutDuration defaultElementTimeOutDuration
            = new TimeOutDuration(timeOutDuration.toMillis(), TimeUnit.MILLISECONDS);

    public DefaultMobilePage() {
        this(new MobileElementDecorator(DriverManager.getDriver(), defaultElementTimeOutDuration));
    }

    protected DefaultMobilePage(FieldDecorator fieldDecorator) {
        super(fieldDecorator);
    }
}

package com.linkedin.automation.controls;

import com.linkedin.automation.core.device.DeviceManager;
import com.linkedin.automation.page_elements.element.IStaticText;
import com.linkedin.automation.page_elements.element.WrappedMobileElement;
import io.appium.java_client.MobileBy;
import org.openqa.selenium.WebElement;

public class StaticText extends WrappedMobileElement implements IStaticText {

    public StaticText(WebElement wrappedElement) {
        super(wrappedElement);
    }

    @Override
    public String getStaticText() {
        try {
            if (DeviceManager.getCurrentDevice().isIOS()) {
                return getWrappedDriver().findElement(MobileBy.IosUIAutomation(".staticTexts()")).getText();
            } else {
                return getWrappedDriver().findElement(MobileBy.className("android.widget.TextView")).getText();
            }
        } catch (Exception ignored) {
        }
        return getWrappedElement().getText();
    }

}

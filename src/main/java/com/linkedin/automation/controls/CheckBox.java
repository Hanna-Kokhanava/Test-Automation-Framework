package com.linkedin.automation.controls;

import com.linkedin.automation.core.device.DeviceManager;
import com.linkedin.automation.page_elements.element.AbstractCheckBox;
import org.openqa.selenium.WebElement;

import java.util.Objects;

public class CheckBox extends AbstractCheckBox {
    public CheckBox(WebElement wrappedElement) {
        super(wrappedElement);
    }

    @Override
    public boolean isChecked() {
        if (DeviceManager.getCurrentDevice().isIOS()) {
            String value = getWrappedElement().getAttribute("value");
            return !Objects.isNull(value) && (value.equals("1") || value.equals("true"));
        } else {
            return isAttributeChecked() || isAttributeSelected();
        }
    }

    private boolean isAttributeSelected() {
        return Boolean.parseBoolean(getWrappedElement().getAttribute("selected"));
    }

    private boolean isAttributeChecked() {
        return Boolean.parseBoolean(getWrappedElement().getAttribute("checked"));
    }
}


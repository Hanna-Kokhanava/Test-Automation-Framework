package com.linkedin.automation.configuration;

import com.google.inject.AbstractModule;
import com.linkedin.automation.page_elements.handlers.scroll.AndroidDefaultScrollHandler;
import com.linkedin.automation.page_elements.handlers.scroll.DefaultScrollHandler;

public class ControlHandlersConfigModule extends AbstractModule {
    @Override
    protected void configure() {
        scrollConfigure();
    }

    private void scrollConfigure() {
        Class<? extends DefaultScrollHandler> scrollableClass;

//        if (Device.DeviceType.IOS == DeviceManager.getDeviceTypeFromConfigFile().os()) {
//            scrollableClass = IOSDefaultScrollHandler.class;
//        } else {
        scrollableClass = AndroidDefaultScrollHandler.class;
//        }
        bind(DefaultScrollHandler.class).to(scrollableClass);
    }
}


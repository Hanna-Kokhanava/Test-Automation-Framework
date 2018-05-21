package com.linkedin.automation.services.ui.handlers;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.linkedin.automation.configuration.ControlHandlersConfigModule;
import com.linkedin.automation.page_elements.handlers.scroll.DefaultScrollHandler;
import io.appium.java_client.pagefactory.Widget;

/**
 * @author Hleb_Halkouski on 4/13/17.
 */
public class LgControlHandlersFactory {

    private static ThreadLocal<ControlHandlersConfigModule> instance = ThreadLocal.withInitial(ControlHandlersConfigModule::new);

    private static Injector getInjector() {
        return Guice.createInjector(instance.get());
    }

    private static DefaultScrollHandler createScrollHandler() {
        return getInjector().getInstance(DefaultScrollHandler.class);
    }

    public static DefaultScrollHandler createScrollHandler(Widget widget) {
        DefaultScrollHandler defaultScrollHandler = createScrollHandler();
        defaultScrollHandler.setWidget(widget);
        return defaultScrollHandler;
    }

}

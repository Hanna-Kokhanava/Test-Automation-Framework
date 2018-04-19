package com.linkedin.automation.core.pagefactory;

import com.linkedin.automation.page_elements.block.MobileBlock;
import io.appium.java_client.pagefactory.OverrideWidget;
import io.appium.java_client.remote.AutomationName;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.InvocationTargetException;

import static io.appium.java_client.remote.MobilePlatform.ANDROID;
import static io.appium.java_client.remote.MobilePlatform.IOS;

public class OverrideWidgetReader {
    private static final Class<? extends MobileBlock> EMPTY = MobileBlock.class;
    private static final String HTML = "html";
    private static final String ANDROID_UI_AUTOMATOR = "androidUIAutomator";
    private static final String IOS_XCUIT_AUTOMATION = "iOSXCUITAutomation";
    private static final String IOS_UI_AUTOMATION = "iOSUIAutomation";
    private static final String SELENDROID = "selendroid";

    @SuppressWarnings("unchecked")
    private static Class<? extends MobileBlock> getConvenientClass(Class<? extends MobileBlock> declaredClass,
                                                                   AnnotatedElement annotatedElement, String method) {
        Class<? extends MobileBlock> convenientClass;
        OverrideWidget overrideWidget = annotatedElement.getAnnotation(OverrideWidget.class);

        try {
            if (overrideWidget == null || (convenientClass =
                    (Class<? extends MobileBlock>) OverrideWidget.class
                            .getDeclaredMethod(method, new Class[]{}).invoke(overrideWidget))
                    .equals(EMPTY)) {
                convenientClass = declaredClass;
            }
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        if (!declaredClass.isAssignableFrom(convenientClass)) {
            throw new IllegalArgumentException(
                    new InstantiationException(declaredClass.getName()
                            + " is not assignable from "
                            + convenientClass.getName()));
        }

        return convenientClass;

    }

    public static Class<? extends MobileBlock> getMobileNativeWidgetClass(Class<? extends MobileBlock> declaredClass,
                                                                          AnnotatedElement annotatedElement,
                                                                          String platform, String automation) {
        String transformedPlatform = String.valueOf(platform).toUpperCase().trim();
        String transformedAutomation = String.valueOf(automation).toUpperCase().trim();

        if (ANDROID.equalsIgnoreCase(transformedPlatform)) {
            if (AutomationName.SELENDROID.equalsIgnoreCase(transformedAutomation)) {
                return getConvenientClass(declaredClass, annotatedElement, SELENDROID);
            }
            return getConvenientClass(declaredClass, annotatedElement, ANDROID_UI_AUTOMATOR);
        }
        if (IOS.equalsIgnoreCase(transformedPlatform)) {
            if (AutomationName.IOS_XCUI_TEST.equalsIgnoreCase(transformedAutomation)) {
                return getConvenientClass(declaredClass, annotatedElement, IOS_XCUIT_AUTOMATION);
            }
            return getConvenientClass(declaredClass, annotatedElement, IOS_UI_AUTOMATION);
        }

        return getConvenientClass(declaredClass, annotatedElement, HTML);
    }

}

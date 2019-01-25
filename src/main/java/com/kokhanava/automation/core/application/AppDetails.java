package com.kokhanava.automation.core.application;

import com.kokhanava.automation.core.tools.files.property.PropertyLoader;

import java.util.Objects;

/**
 * Created on 25.01.2019
 */
public class AppDetails {

    enum AppName {

        LINKEDIN("com.linkedin.android", "com.linkedin.android.authenticator.LaunchActivity");

        private final String appPackage;
        private final String appActivity;

        AppName(String appPackage, String appActivity) {
            this.appPackage = appPackage;
            this.appActivity = appActivity;
        }

        public String getAppPackage() {
            return appPackage;
        }

        public String getAppActivity() {
            return appActivity;
        }
    }

    private static AppName appName;

    public static String getAppPackage() {
        return getAppName().getAppPackage();
    }

    public static String getAppActivity() {
        return getAppName().getAppActivity();
    }

    private static AppName getAppName() {
        if (Objects.isNull(appName)) {
            appName = AppName.valueOf(PropertyLoader.get(PropertyLoader.MobileProperty.APP_NAME).toUpperCase());
        }
        return appName;
    }
}

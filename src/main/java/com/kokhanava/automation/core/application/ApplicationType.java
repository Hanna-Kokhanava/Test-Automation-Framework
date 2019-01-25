package com.kokhanava.automation.core.application;

/**
 * Created on 17.04.2018
 * Enumeration of available types that are used for selecting necessary
 * FindBy annotation among Android, IOS & WebUI
 */
public enum ApplicationType {
    /**
     * The native Android app
     */
    NATIVE_ANDROID_APP,

    /**
     * The native IOS app
     */
    NATIVE_IOS_APP,

    /**
     * Web app
     */
    WEB_APP
}

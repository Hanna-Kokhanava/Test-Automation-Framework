package com.linkedin.automation.core.device;

import com.google.gson.annotations.SerializedName;
import com.linkedin.automation.core.driver.managers.DriverManager;
import com.linkedin.automation.core.tools.HostMachine;
import io.appium.java_client.remote.AutomationName;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.server.browserlaunchers.Sleeper;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.linkedin.automation.core.device.Device.DeviceType.*;

/**
 * Created on 01.04.2018
 */
public class Device {
    private final String platformVersionPattern = "^(\\d{1,2}(\\.\\d{1,2})?)(\\.\\d{1,2})?$";

    @XmlAttribute
    private String deviceName;

    @XmlAttribute
    private String udid;

    @XmlAttribute
    private DeviceType deviceType;

    @XmlElement
    private HostMachine appium;

    @XmlAttribute
    private String platformVersion;

    public String getDeviceName() {
        return deviceName;
    }

    public String getDeviceUDID() {
        return udid;
    }

    public DeviceType getDeviceType() {
        return deviceType;
    }

    public HostMachine getAppiumHostMachine() {
        return appium;
    }

    public Double getOsVersion() {
        Pattern pattern = Pattern.compile(platformVersionPattern);
        Matcher matcher = pattern.matcher(platformVersion.replace(",", "."));
        if (matcher.find()) {
            return Double.parseDouble(matcher.group(1));
        }
        return Double.parseDouble(platformVersion.replace(",", "."));
    }

    public String getAutomationName() {
        switch (deviceType.os()) {
            case IOS: {
                if (getOsVersion() >= 9.3) {
                    return AutomationName.IOS_XCUI_TEST;
                }
                break;
            }
            case ANDROID: {
                if (getOsVersion() >= 5.0) {
                    return AutomationName.ANDROID_UIAUTOMATOR2;
                }
                break;
            }
        }
        return AutomationName.APPIUM;
    }

    @XmlEnum
    public enum DeviceType {
        ANY("") {
            @Override
            public boolean is(DeviceType compareWith) {
                return this == compareWith;
            }
        },

        PHONE(""),

        TABLET(""),

        ANDROID("") {
            @Override
            public DeviceType os() {
                return ANDROID;
            }
        },

        IOS("") {
            @Override
            public DeviceType os() {
                return IOS;
            }
        },

        @SerializedName("android-phone")
        @XmlEnumValue("android-phone")
        ANDROID_PHONE("android-phone", "android phone", "android", "phone") {
            @Override
            public DeviceType os() {
                return ANDROID;
            }

            @Override
            public DeviceType size() {
                return PHONE;
            }
        },

        @SerializedName("android-tablet")
        @XmlEnumValue("android-tablet")
        ANDROID_TABLET("android-tablet", "android tablet", "tablet") {
            @Override
            public DeviceType os() {
                return ANDROID;
            }

            @Override
            public DeviceType size() {
                return TABLET;
            }
        },

        @SerializedName("iphone")
        @XmlEnumValue("iphone")
        IPHONE("iphone", "iphone retina", "ipod") {
            @Override
            public DeviceType os() {
                return IOS;
            }

            @Override
            public DeviceType size() {
                return PHONE;
            }
        },

        @SerializedName("ipad")
        @XmlEnumValue("ipad")
        IPAD("ipad", "ipad retina") {
            @Override
            public DeviceType os() {
                return IOS;
            }

            @Override
            public DeviceType size() {
                return TABLET;
            }
        };

        private final String[] partOfDeviceName;

        DeviceType(String... partOfDeviceName) {
            this.partOfDeviceName = partOfDeviceName;
        }

        public String[] getPartOfDeviceName() {
            return partOfDeviceName;
        }

        public boolean is(DeviceType compareWith) {
            return this == compareWith || this.os().is(compareWith);
        }

        public DeviceType os() {
            return ANY;
        }

        public DeviceType size() {
            return ANY;
        }
    }

    /**
     * Touch screen gestures.
     */
    public static class TouchScreen {

        /**
         * Gets the device window size.
         *
         * @return the size
         */
        public static Dimension getSize() {
            return DriverManager.getDriver().manage().window().getSize();
        }
    }

    /**
     * Universal keyboard implementation for Android/IOS
     */
    public static class Keyboard {

        /**
         * Press any arbitrary keys (or generate key event).
         *
         * @param actionKeys the action keys sequence
         */
        public static void press(Key... actionKeys) {
            DeviceType os = DeviceManager.getCurrentDevice().getDeviceType().os();
            switch (os) {
                case ANDROID:
                    for (Key key : actionKeys) {
                        DriverManager.getAndroidDriver().pressKeyCode(key.getAndroidCode());
                    }
                    break;
                case IOS:
                    if (AutomationName.IOS_XCUI_TEST.equalsIgnoreCase(DriverManager.getDriver().getAutomationName())) {
                        for (Key key : actionKeys) {
                            DriverManager.getIOSDriver().findElementByIosNsPredicate("name == \""
                                    + key.getIOSName() + "\" AND (type == \"XCUIElementTypeButton\" OR type == \"XCUIElementTypeKey\")").click();
                        }
                        break;
                    }

                    // For UIAutomation
                    for (Key key : actionKeys) {
                        String tapIOSKeyScript =
                                "UIATarget.localTarget().frontMostApp().keyboard()"
                                        + ".elements().firstWithPredicate(\"name CONTAINS[c] '" + key.getIOSName().toLowerCase() + "'\").tap()";
                        DriverManager.getDriver().executeScript(tapIOSKeyScript);
                    }
                    break;
                default:
                    throw new RuntimeException("Pressing keys is not implemented for " + os);
            }
        }

        public static void hideKeyboard() {
            try {
                Sleeper.sleepTight(2);
                DriverManager.getDriver().hideKeyboard();
            } catch (WebDriverException e1) {
                try {
                    if (DeviceManager.getCurrentDevice().isIOS())
                        press(Key.HIDE);
                } catch (WebDriverException e2) {
                    System.out.println("Keyboard already hide");
                }
                System.out.println("Keyboard is hide");
            }
        }
    }

    /**
     * Checks if device is of Android type.
     *
     * @return true, if is android
     */
    public boolean isAndroid() {
        return deviceType.is(ANDROID);
    }

    /**
     * Checks if device is of IOS type.
     *
     * @return true, if is ios
     */
    public boolean isIOS() {
        return deviceType.is(IOS);
    }

    /**
     * Checks if device is tablet.
     *
     * @return true, if is tablet
     */
    public boolean isTablet() {
        return deviceType.is(TABLET);
    }

    /**
     * Checks if device is phone.
     *
     * @return true, if is phone
     */
    public boolean isPhone() {
        return deviceType.is(PHONE);
    }

    /**
     * Checks if device is android phone.
     *
     * @return true, if is android phone
     */
    public boolean isAndroidPhone() {
        return deviceType.is(ANDROID_PHONE);
    }

    /**
     * Checks if device is android tablet.
     *
     * @return true, if is android tablet
     */
    public boolean isAndroidTablet() {
        return deviceType.is(ANDROID_TABLET);
    }

    /**
     * Checks if device is iphone.
     *
     * @return true, if is i phone
     */
    public boolean isIPhone() {
        return deviceType.is(IPHONE);
    }

    /**
     * Checks if device is ipad.
     *
     * @return true, if is i pad
     */
    public boolean isIPad() {
        return deviceType.is(IPAD);
    }

}

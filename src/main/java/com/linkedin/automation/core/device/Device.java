package com.linkedin.automation.core.device;

import com.linkedin.automation.core.tools.HostMachine;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created on 01.04.2018
 */
public class Device {
    private final String osVersionPattern = "^(\\d{1,2}(\\.\\d{1,2})?)(\\.\\d{1,2})?$";

    @XmlAttribute
    private String deviceName;

    @XmlAttribute
    private String deviceUDID;

    @XmlAttribute
    private DeviceType deviceType;

    @XmlElement
    private HostMachine hostMachine;

    @XmlAttribute
    private String osVersion;

    public String getDeviceName() {
        return deviceName;
    }

    public String getDeviceUDID() {
        return deviceUDID;
    }

    public DeviceType getDeviceType() {
        return deviceType;
    }

    public HostMachine getAppiumHostMachine() {
        return hostMachine;
    }

    public Double getOsVersion() {
        Pattern pattern = Pattern.compile(osVersionPattern);
        Matcher matcher = pattern.matcher(osVersion.replace(",", "."));
        if (matcher.find()) {
            return Double.parseDouble(matcher.group(1));
        }
        return Double.parseDouble(osVersion.replace(",", "."));
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

        ANDROID(""),

        @XmlEnumValue("android-phone")
        ANDROID_PHONE("android-phone", "phone") {
            @Override
            public DeviceType os() {
                return ANDROID;
            }
        },

        @XmlEnumValue("android-tablet")
        ANDROID_TABLET("android-tablet", "tablet") {
            @Override
            public DeviceType os() {
                return ANDROID;
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
    }
}

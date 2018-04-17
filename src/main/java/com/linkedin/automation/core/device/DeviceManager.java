package com.linkedin.automation.core.device;

import com.linkedin.automation.core.driver.managers.DriverManager;
import com.linkedin.automation.core.tools.files.ProjectDir;
import com.linkedin.automation.core.tools.files.PropertyLoader;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

public class DeviceManager {

    @XmlRootElement
    private static class Devices {
        @XmlElement(name = "device")
        private List<Device> devicesList;
    }

    /**
     * Gets list of all {@link Device} items from devices.xml
     */
    private static final List<Device> devicesList = ProjectDir.readFromResource(Devices.class,
            PropertyLoader.get(PropertyLoader.Property.DEVICES_XML)).devicesList;

    private static final ThreadLocal<Device> currentDevice = new ThreadLocal<>();

    public static Device getCurrentDevice() {
        if (currentDevice.get() == null && DriverManager.getDriverType() == DriverManager.DriverType.APPIUM) {
            String udid = PropertyLoader.get(PropertyLoader.Property.DEVICE_UDID, "");
            if (!udid.equals(""))
                setCurrentDevice(DeviceManager.getDevice(udid));
        }
        return currentDevice.get();
    }

    /**
     * Set {@link Device) instance as current device
     *
     * @param device
     */
    public static void setCurrentDevice(Device device) {
        currentDevice.remove();
        currentDevice.set(device);
        System.out.println("Set current device: " + device);
    }

    /**
     * Returns {@link Device} by UDID from XML file
     *
     * @param udid device UDID
     */
    public static Device getDevice(String udid) {
        for (Device device : devicesList) {
            if (device.getDeviceUDID().equalsIgnoreCase(udid)) {
                return device;
            }
        }
        throw new RuntimeException("Device with UDID [" + udid + "] was not found");
    }

    /**
     * The current device type.
     */
    private static Device.DeviceType deviceTypeFromConfig;

    /**
     * Extract device type based on config properties and uses a heuristic to
     * determine the most likely device. If unable to determine the device, it
     * will throw runtime exception.
     *
     * @return the most likely device based on given device name
     */
    public static Device.DeviceType getDeviceTypeFromConfigFile() {
        if (deviceTypeFromConfig == null) {

            String deviceType = PropertyLoader.get(PropertyLoader.Property.DEVICE_TYPE).toLowerCase();

            Device.DeviceType mostLikely = null;
            String previousMatch = null;

            for (Device.DeviceType device : Device.DeviceType.values()) {
                for (String matcher : device.getPartOfDeviceName()) {
                    if ("".equals(matcher)) {
                        continue;
                    }
                    matcher = matcher.toLowerCase();
                    if (isExactMatch(deviceType, matcher)) {
                        return device;
                    }
                    if (isCurrentDevice(deviceType, matcher)
                            && isBetterMatch(previousMatch, matcher)) {
                        previousMatch = matcher;
                        mostLikely = device;
                    }
                }
            }
            if (mostLikely != null)
                deviceTypeFromConfig = mostLikely;
            else
                throw new RuntimeException(String.format("Unable to detect device by name '%s'", deviceType));
        }

        return deviceTypeFromConfig;
    }

    /**
     * Decides whether the previous match is better or not than the current
     * match. If previous match is null, the newer match is always better.
     *
     * @param previous the previous match
     * @param matcher  the newer match
     * @return true if newer match is better, false otherwise
     */
    private static boolean isBetterMatch(String previous, String matcher) {
        return previous == null || matcher.length() >= previous.length();
    }

    private static boolean isCurrentDevice(String deviceName, String matchAgainst) {
        return deviceName.contains(matchAgainst);
    }

    private static boolean isExactMatch(String deviceName, String matchAgainst) {
        return matchAgainst.equals(deviceName);
    }
}

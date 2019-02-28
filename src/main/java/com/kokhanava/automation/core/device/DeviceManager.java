package com.kokhanava.automation.core.device;

import com.kokhanava.automation.core.driver.managers.mobile.MobileDriverManager;
import com.kokhanava.automation.core.logger.Logger;
import com.kokhanava.automation.core.tools.commands.Command;
import com.kokhanava.automation.core.tools.commands.CommandExecutor;
import com.kokhanava.automation.core.tools.files.ProjectDir;
import com.kokhanava.automation.core.tools.files.property.PropertyLoader;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Manages devices from xml file
 */
public class DeviceManager {

    @XmlRootElement
    private static class Devices {
        @XmlElement(name = "device")
        private List<Device> devicesList;
    }

    /**
     * Gets list of all {@link Device} items from devices.xml
     */
    private static final List<Device> actualDevicesList = ProjectDir.readFromResource(Devices.class,
            PropertyLoader.get(PropertyLoader.MobileProperty.DEVICES_XML)).devicesList;

    private static final ThreadLocal<Device> currentDevice = new ThreadLocal<>();

    /**
     * Returns current device
     * If it's not set - find in devices list from xml by UDID from properties file
     *
     * @return {@link Device} instance
     */
    public static Device getCurrentDevice() {
        Logger.debug("Get current Device instance");
        if (Objects.isNull(currentDevice.get())
                && MobileDriverManager.getDriverType() == MobileDriverManager.DriverType.APPIUM) {
            String udid = PropertyLoader.get(PropertyLoader.MobileProperty.DEVICE_UDID, "");
            if (!udid.isEmpty())
                setCurrentDevice(DeviceManager.getDevice(udid));
        }
        return currentDevice.get();
    }

    /**
     * Sets {@link Device) instance as current device, removed previous one
     *
     * @param device {@link Device} instance
     */
    public static void setCurrentDevice(Device device) {
        Logger.debug("Set current Device instance with name : " + device.getDeviceName());
        currentDevice.remove();
        currentDevice.set(device);
    }

    /**
     * Returns {@link Device} by its UDID from XML file
     *
     * @param udid device UDID
     */
    public static Device getDevice(String udid) {
        return actualDevicesList.stream()
                .filter(device -> device.getDeviceUDID().equalsIgnoreCase(udid))
                .findFirst()
                .orElseThrow(() -> new NullPointerException("Device with UDID [" + udid + "] was not found"));
    }

    /**
     * The current device type
     */
    private static Device.DeviceType deviceTypeFromConfig;

    /**
     * Extract device type based on config properties and uses a heuristic to
     * determine the most likely device. If unable to determine the device, it
     * will throw runtime exception.
     *
     * @return {@link Device.DeviceType} the most likely device type based on given device name
     */
    public static Device.DeviceType getDeviceTypeFromConfigFile() {
        if (Objects.isNull(deviceTypeFromConfig)) {
            var deviceType = PropertyLoader.get(PropertyLoader.MobileProperty.DEVICE_TYPE).toLowerCase();

            Device.DeviceType mostLikely = null;
            String previousMatch = null;

            for (Device.DeviceType device : Device.DeviceType.values()) {
                for (String matcher : device.getPartOfDeviceName()) {
                    if (matcher.isEmpty()) {
                        continue;
                    }
                    matcher = matcher.toLowerCase();
                    if (isExactMatch(deviceType, matcher)) {
                        return device;
                    }
                    if (isCurrentDevice(deviceType, matcher) && isBetterMatch(previousMatch, matcher)) {
                        previousMatch = matcher;
                        mostLikely = device;
                    }
                }
            }
            deviceTypeFromConfig = Objects.requireNonNull(mostLikely,
                    "Unable to detect device by name [" + deviceType + "]");
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
        return Objects.isNull(previous) || matcher.length() >= previous.length();
    }

    private static boolean isCurrentDevice(String deviceName, String matchAgainst) {
        return deviceName.contains(matchAgainst);
    }

    private static boolean isExactMatch(String deviceName, String matchAgainst) {
        return matchAgainst.equals(deviceName);
    }


    //For GRID

    /**
     * Get list of {@link Device} from XML file with {@link Device.DeviceType} extracted from config properties
     *
     * @return {@link Device} list
     */
    public static List<Device> getDevicesListByConfigType() {
        List<Device> devices = actualDevicesList.stream().filter(device -> getDeviceTypeFromConfigFile() ==
                device.getDeviceType()).collect(Collectors.toList());

        if (!devices.isEmpty()) {
            return devices;
        }
        throw new RuntimeException("Device [" + getDeviceTypeFromConfigFile() + "] not found");
    }

    /**
     * Check is device connected to appium host machine
     *
     * @param device checkable device
     * @return {@code true} if connected
     */
    public static boolean isDeviceConnectedToHostMachine(Device device) {
        String commandResult;
        if (device.isAndroid()) {
            commandResult = CommandExecutor.execute(device.getAppiumHostMachine(), Command.ADB_DEVICES_UDID_LIST);
        } else {
            commandResult = CommandExecutor.execute(device.getAppiumHostMachine(), Command.IOS_IDEVICE_UDID_LIST);
        }
        var devicesUDID = Arrays.asList(commandResult.split("\n"));
        return devicesUDID.contains(device.getDeviceUDID());
    }
}

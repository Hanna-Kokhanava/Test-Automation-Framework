package com.linkedin.automation.core.device;

import com.linkedin.automation.core.tools.files.PropertyLoader;
import com.linkedin.automation.core.tools.commands.Command;
import com.linkedin.automation.core.tools.commands.CommandExecutor;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Hleb_Halkouski on 10/19/16.
 */
public class DeviceManager {

    private static final List<Device> actualDevicesList =
            readDevicesFromFile(PropertyLoader.get(PropertyLoader.Property.DEVICES_XML));

    private static final ThreadLocal<Device> currentDevice = new ThreadLocal<>();

    public static Device getCurrentDevice() {
        return currentDevice.get();
    }

    public static void setCurrentDevice(Device device) {
        currentDevice.remove();
        currentDevice.set(device);
    }

    public static Device getDevice(String udid) {
        for (Device device : actualDevicesList) {
            if (device.getDeviceUDID().equalsIgnoreCase(udid)) {
                return device;
            }
        }
        throw new RuntimeException("Device with UDID [" + udid + "] not found");
    }

    public static List<Device> getDevicesListByConfigType() {
        List<Device> devices = actualDevicesList.stream()
                .filter(device -> getDeviceTypeFromConfigFile() ==
                device.getDeviceType()).collect(Collectors.toList());

        if (!devices.isEmpty()) {
            return devices;
        }
        throw new RuntimeException("Device [" + getDeviceTypeFromConfigFile() + "] not found");
    }

    private static List<Device> readDevicesFromFile(String path) {
        /*
          Handler for &lt;users&gt; XML tag in <code>users</code> XML file.
         */
        @XmlRootElement
        class Devices {
            @XmlElement(name = "device")
            private List<Device> devicesList;
        }

        Devices devices;
        try {
            InputStream stream = DeviceManager.class.getClassLoader().getResourceAsStream(path);
            if (stream != null)
                devices = (Devices) JAXBContext.newInstance(Devices.class)
                        .createUnmarshaller()
                        .unmarshal(stream);
            else
                throw new RuntimeException("File " + path + " could not be found");
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
        return devices.devicesList;
    }

    public static boolean isDeviceConnectedToHostMachine(Device device) {
        String commandResult = CommandExecutor.execute(Command.ADB_DEVICES_UDID_LIST);
        List<String> devicesUDID = Arrays.asList(commandResult.split("\n"));
        return devicesUDID.contains(device.getDeviceUDID());
    }

    private static Device.DeviceType deviceTypeFromConfig;

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
                throw new RuntimeException("Unable to detect device by name '" + deviceType + "'");
        }
        return deviceTypeFromConfig;
    }

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

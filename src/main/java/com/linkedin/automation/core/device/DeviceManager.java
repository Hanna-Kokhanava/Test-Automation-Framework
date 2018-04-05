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

/**
 * @author Hleb_Halkouski on 10/19/16.
 */
public class DeviceManager {

    //Get list of all devices from devices.xml
    private static final List<Device> devicesList =
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
        for (Device device : devicesList) {
            if (device.getDeviceUDID().equalsIgnoreCase(udid)) {
                return device;
            }
        }
        throw new RuntimeException("Device with UDID [" + udid + "] not found");
    }

    private static List<Device> readDevicesFromFile(String path) {
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
            else {
                throw new RuntimeException("File " + path + " could not be found");
            }
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
        return devices.devicesList;
    }

}

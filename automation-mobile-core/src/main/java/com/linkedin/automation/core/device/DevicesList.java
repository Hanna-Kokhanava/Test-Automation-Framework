package com.linkedin.automation.core.device;

import java.util.ArrayList;
import java.util.List;

public class DevicesList extends ArrayList {
    public DevicesList(List<Device.DeviceType> deviceTypes) {
        super(deviceTypes);
    }

    public boolean is(Device.DeviceType deviceType) {
        for (Object o : this)
            if (deviceType.is((Device.DeviceType) o))
                return true;
        return false;
    }
}
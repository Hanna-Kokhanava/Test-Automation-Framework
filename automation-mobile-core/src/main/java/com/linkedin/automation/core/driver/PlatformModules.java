package com.linkedin.automation.core.driver;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.linkedin.automation.core.device.Device;
import com.linkedin.automation.core.device.DeviceManager;
import com.linkedin.automation.core.driver.config.*;

/**
 * Created on 20.03.2018
 * Will be used to bind interface methods implementation to the specific platform implementation
 */
public class PlatformModules extends AbstractModule {

    @Override
    protected void configure() {
        if (DeviceManager.getDeviceTypeFromConfigFile().os() == Device.DeviceType.IOS) {
            bind(ICommandStartAppium.class).to(IOSPlatformConfig.class).in(Scopes.SINGLETON);
            bind(IAutomationNameConfig.class).to(IOSPlatformConfig.class).in(Scopes.SINGLETON);
            bind(IPlatformConfig.class).to(IOSPlatformConfig.class).in(Scopes.SINGLETON);
        } else {
            bind(ICommandStartAppium.class).to(AndroidPlatformConfig.class).in(Scopes.SINGLETON);
            bind(IAutomationNameConfig.class).to(AndroidPlatformConfig.class).in(Scopes.SINGLETON);
            bind(IPlatformConfig.class).to(AndroidPlatformConfig.class).in(Scopes.SINGLETON);
        }
    }
}

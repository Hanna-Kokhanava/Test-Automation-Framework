package com.kokhanava.automation.core.driver;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.kokhanava.automation.core.device.Device;
import com.kokhanava.automation.core.device.DeviceManager;
import com.kokhanava.automation.core.driver.config.AndroidPlatformConfig;
import com.kokhanava.automation.core.driver.config.ICommandStartAppiumServer;
import com.kokhanava.automation.core.driver.config.IMobilePlatformConfig;

/**
 * Created on 20.03.2018
 * Will be used to bind interface methods implementation to the specific platform implementation
 */
public class PlatformModules extends AbstractModule {
    @Override
    protected void configure() {
        if (DeviceManager.getDeviceTypeFromConfigFile().os() == Device.DeviceType.ANDROID) {
            bind(ICommandStartAppiumServer.class).to(AndroidPlatformConfig.class).in(Scopes.SINGLETON);
            bind(IMobilePlatformConfig.class).to(AndroidPlatformConfig.class).in(Scopes.SINGLETON);
        }
    }
}

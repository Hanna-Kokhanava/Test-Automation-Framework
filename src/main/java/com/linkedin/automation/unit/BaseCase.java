package com.linkedin.automation.unit;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.linkedin.automation.core.creators.IDriverCreator;
import com.linkedin.automation.core.driver.DriverModules;
import com.linkedin.automation.core.driver.dependencies.IDependencies;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

/**
 * Created on 10.03.2018
 */
public class BaseCase {
    @Inject
    private IDependencies dependencies;
    @Inject
    protected IDriverCreator driverCreator;

    {
        servicesInject();
    }

    private void servicesInject() {
        Guice.createInjector(new DriverModules())
                .injectMembers(this);
    }

    @BeforeClass(description = "Configure dependencies and start Appium server", alwaysRun = true)
    public void configDependencies() {
        dependencies.configureDependencies();
    }

    @BeforeClass(description = "Create driver", dependsOnMethods = {"configDependencies"})
    public void setUp() throws Exception {
        driverCreator.createDriver();
    }

    @AfterClass(description = "Quit driver", alwaysRun = true)
    public void tearDown() {
        driverCreator.closeDriver();
    }
}

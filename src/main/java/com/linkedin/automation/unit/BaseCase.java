package com.linkedin.automation.unit;

import com.linkedin.automation.driver.DriverCreator;
import com.linkedin.automation.driver.IDriverCreator;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

/**
 * Created on 10.03.2018
 */
public class BaseCase {
    //TODO @Inject Guice lib
    protected IDriverCreator driverCreator = new DriverCreator();

    @BeforeClass(description = "Base case configuration set up")
    public void setUp() throws Exception {
        driverCreator.createDriver("en");
    }

    @AfterClass(description = "Quit driver", alwaysRun = true)
    public void tearDown() {
        driverCreator.closeDriver();
    }
}

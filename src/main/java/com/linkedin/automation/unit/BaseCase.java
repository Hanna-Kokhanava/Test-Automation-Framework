package com.linkedin.automation.unit;

import org.testng.annotations.BeforeClass;

import static com.linkedin.automation.driver.DriverManager.createDriver;

/**
 * Created on 10.03.2018
 */
public class BaseCase {

    @BeforeClass(description = "Base case configuration set up")
    public void setUp() throws Exception {
        createDriver();
    }
}

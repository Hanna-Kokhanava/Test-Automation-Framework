package com.kokhanava.automation.testscripts.mobile;

import com.kokhanava.automation.core.logger.Logger;
import com.kokhanava.automation.unit.BaseCase;
import org.testng.annotations.Test;

/**
 * Created on 10.03.2018
 */
public class LaunchTest extends BaseCase {

    @Test(description = "Go to base page, refresh and check title")
    public void openBasePage() {
        Logger.info("Test to check base configuration was launched");
    }
}

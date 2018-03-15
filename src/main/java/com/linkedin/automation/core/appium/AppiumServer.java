package com.linkedin.automation.core.appium;

import org.apache.http.client.utils.URIBuilder;

/**
 * Created on 15.03.2018
 */
public class AppiumServer extends BaseAppiumServer {

    @Override
    public void startServer() {
        //TODO execute command

        URIBuilder uriBuilder = new URIBuilder().setScheme("http");
        uriBuilder.setPort(4881);
        uriBuilder.setHost("localhost");
        waitForServerStarts((Void) -> checkStatus(uriBuilder.setPath(WD_SERVER_STATUS)));
    }
}

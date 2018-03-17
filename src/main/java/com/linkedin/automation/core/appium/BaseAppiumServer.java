package com.linkedin.automation.core.appium;

import org.apache.http.client.utils.URIBuilder;

/**
 * Created on 15.03.2018
 */
public abstract class BaseAppiumServer extends AbstractServer implements IAppiumServer {

    @Override
    public boolean checkStatus() {
        URIBuilder uriBuilder = new URIBuilder().setScheme("http");
        uriBuilder.setPort(4881);
        uriBuilder.setHost("localhost");
        return checkServerStatus(uriBuilder.setPath(WD_SERVER_STATUS));
    }

    @Override
    public void stopServer() {

    }

    @Override
    public void restartServer() {

    }
}

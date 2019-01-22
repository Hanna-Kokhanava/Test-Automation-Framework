package com.linkedin.automation.core.browser;

import com.linkedin.automation.core.tools.HostMachine;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

/**
 * Created on 21.01.2019
 */
public class Browser {
    @XmlAttribute
    private String browserName;

    @XmlAttribute
    private String platformName;

    @XmlElement
    private HostMachine host;


    public String getBrowserName() {
        return browserName;
    }

    public String getPlatformName() {
        return platformName;
    }

    public HostMachine getHost() {
        return host;
    }
}

package com.kokhanava.automation.core.browser;

import com.kokhanava.automation.core.tools.HostMachine;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

/**
 * Browser instance to hold specific attributes.
 * Used to parse xml file
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

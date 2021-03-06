package com.kokhanava.automation.core.driver.managers.web;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

/**
 * Driver Repository instance to hold specific information
 * Created on 07.02.2019
 */
public class DriverRepository {

    @XmlAttribute
    private String name;

    @XmlElement
    private String version;

    @XmlAttribute
    private String os;

    @XmlElement
    private String location;

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }

    public String getOs() {
        return os;
    }

    public String getLocation() {
        return location;
    }
}

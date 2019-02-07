package com.kokhanava.automation.core.driver.managers.web;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;

/**
 * Created on 07.02.2019
 */
public class Driver {

    @XmlAttribute
    private String id;

    @XmlElement
    private String version;

    @XmlElement
    private OS os;

    @XmlElement
    private String fileLocation;

    public String getId() {
        return id;
    }

    public String getVersion() {
        return version;
    }

    public String getOs() {
        return os.toString();
    }

    public String getFileLocation() {
        return fileLocation;
    }

    //TODO make it as global constants (conflicts with OS?)
    @XmlEnum
    public enum OS {
        @XmlEnumValue("mac")
        MAC("mac"),

        @XmlEnumValue("windows")
        WINDOWS("windows");

        private String os;

        OS(String os) {
            this.os = os;
        }

        @Override
        public String toString() {
            return os;
        }
    }
}

package com.linkedin.automation.core.tools;

import com.linkedin.automation.core.tools.commands.CommandExecutor;

import javax.xml.bind.annotation.XmlAttribute;

/**
 * Created on 01.04.2018
 */
public class HostMachine {
    /**
     * [username:password@]hostname:port
     */
    private final static String URI_PATTERN = "^(?:(.+):(.+)@)?(.+):(\\d+)$";

    @XmlAttribute
    private String username;

    @XmlAttribute
    private String password;

    @XmlAttribute
    private String hostname;

    @XmlAttribute
    private String port;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getHostname() {
        return CommandExecutor.getHostNameOfLocalhost();
    }

}

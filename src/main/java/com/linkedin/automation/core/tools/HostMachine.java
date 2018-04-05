package com.linkedin.automation.core.tools;

import com.linkedin.automation.core.tools.commands.CommandExecutor;

import javax.xml.bind.annotation.XmlAttribute;

/**
 * Created on 01.04.2018
 */
public class HostMachine {

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

    public String getPort() {
        return port;
    }

    public int getPortInt() {
        return Integer.parseInt(port);
    }

    @Override
    public String toString() {
        return "[username=" + username + ", password=" + password + ", hostname=" + hostname + ", port=" + port + "]";
    }
}

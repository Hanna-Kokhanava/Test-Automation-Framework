package com.linkedin.automation.core.proxy;

import com.linkedin.automation.core.tools.HostMachine;
import org.apache.http.client.utils.URIBuilder;

import javax.xml.bind.annotation.XmlAttribute;

public class ProxyHostMachine extends HostMachine {

    private String id;
    private int portAPI;

    /**
     * Gets id of proxy
     *
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * Sets proxy id
     *
     * @param id proxy id
     */
    @XmlAttribute(name = "id")
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets port proxy API
     *
     * @return port proxy API
     */
    public int getPortAPI() {
        return portAPI;
    }

    /**
     * Sets port proxy API
     *
     * @param apiPort API port
     */
    @XmlAttribute(name = "portAPI", required = true)
    public void setApiPort(String apiPort) {// do not rename this method!!!
        this.portAPI = Integer.valueOf(apiPort);
    }

    @Override
    public String toString() {
        return super.toString() + "[" + portAPI + "]";
    }

    @Override
    public boolean equals(Object o) {
        return o != null && java.util.Objects.equals(toString(), o.toString());
    }

    public URIBuilder getURIBuilder() {
        String URI_SCHEME = "http";
        return new URIBuilder()
                .setScheme(URI_SCHEME)
                .setHost(getHostname())
                .setPort(getPortAPI());
    }
}
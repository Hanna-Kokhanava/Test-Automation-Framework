package com.linkedin.automation.core.tools.http;

import org.apache.http.impl.client.CloseableHttpClient;

import java.time.Duration;

/**
 * Created on 17.03.2018
 */
public class HttpClientService {
    private static HttpClientHandler httpClientHandler = new HttpClientHandler().enableRequestConfig(Duration.ofMinutes(3));

    public static CloseableHttpClient getHttpClient() {
        return httpClientHandler.getHttpClient();
    }
}

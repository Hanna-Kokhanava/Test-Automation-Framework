package com.linkedin.automation.core.tools.httpclients;

import org.apache.http.impl.client.CloseableHttpClient;

import java.time.Duration;

public class HttpClient {

    private static HttpClientHandler httpClientHandler = new HttpClientHandler().enableRequestConfig(Duration.ofMinutes(3));

    public static CloseableHttpClient getHttpClient() {
        return httpClientHandler.getHttpClient();
    }

    public static void closeHttpClient() {
        httpClientHandler.closeHttpClient();
    }

}

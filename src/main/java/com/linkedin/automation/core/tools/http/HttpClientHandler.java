package com.linkedin.automation.core.tools.http;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;

import java.time.Duration;

/**
 * Created on 17.03.2018
 */
public class HttpClientHandler {
    private CloseableHttpClient httpClient = null;
    private RequestConfig requestConfig;

    public CloseableHttpClient getHttpClient() {
        if (httpClient == null) {
            HttpClientBuilder httpClientBuilder = HttpClients.custom();
            if (requestConfig != null) {
                httpClientBuilder.setDefaultRequestConfig(requestConfig);
            }
            httpClientBuilder.setRetryHandler(new DefaultHttpRequestRetryHandler(5, true));
            httpClient = httpClientBuilder.build();
        }
        return httpClient;
    }

    public HttpClientHandler enableRequestConfig(Duration duration) {
        requestConfig = RequestConfig.custom().
                setConnectTimeout((int) duration.toMillis()).
                setConnectionRequestTimeout((int) duration.toMillis()).
                setSocketTimeout((int) duration.toMillis()).build();
        return this;
    }
}

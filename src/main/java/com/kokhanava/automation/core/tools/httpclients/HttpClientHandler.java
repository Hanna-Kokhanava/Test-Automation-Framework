package com.kokhanava.automation.core.tools.httpclients;

import com.kokhanava.automation.core.logger.Logger;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;

import java.time.Duration;
import java.util.Objects;

/**
 * Created on 05.03.2019
 */
public class HttpClientHandler {
    private static final int RETRY_COUNT = 5;

    private CloseableHttpClient httpClient;
    private RequestConfig requestConfig;

    public CloseableHttpClient getHttpClient() {
        if (Objects.isNull(httpClient)) {
            HttpClientBuilder httpClientBuilder = HttpClients.custom();
            if (Objects.nonNull(requestConfig)) {
                httpClientBuilder.setDefaultRequestConfig(requestConfig);
            }
            httpClientBuilder.setRetryHandler(new DefaultHttpRequestRetryHandler(RETRY_COUNT, true));
            httpClient = httpClientBuilder.build();
        }
        return httpClient;
    }

    public void closeHttpClient() {
        try {
            if (Objects.nonNull(httpClient)) {
                httpClient.close();
                httpClient = null;
            }
        } catch (Exception e) {
            Logger.error("Unable to close an http client", e);
            throw new RuntimeException("Unable to close an http client", e);
        }
    }

    public HttpClientHandler enableRequestConfig(Duration duration) {
        requestConfig = RequestConfig.custom().
                setConnectTimeout((int) duration.toMillis()).
                setConnectionRequestTimeout((int) duration.toMillis()).
                setSocketTimeout((int) duration.toMillis()).build();
        return this;
    }
}

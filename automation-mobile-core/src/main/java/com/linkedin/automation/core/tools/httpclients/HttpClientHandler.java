package com.linkedin.automation.core.tools.httpclients;

import com.linkedin.automation.core.proxy.ProxyHostMachine;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;

import java.time.Duration;

public class HttpClientHandler {

    private CloseableHttpClient httpClient = null;

    private SSLConnectionSocketFactory sslConnectionSocketFactory;
    private RequestConfig requestConfig;
    private HttpHost proxyHost;

    public CloseableHttpClient getHttpClient() {
        if (httpClient == null) {

            HttpClientBuilder httpClientBuilder = HttpClients.custom();
            if (sslConnectionSocketFactory != null) {
                httpClientBuilder.setSSLSocketFactory(sslConnectionSocketFactory);
            }
            if (requestConfig != null) {
                httpClientBuilder.setDefaultRequestConfig(requestConfig);
            }
            if (proxyHost != null) {
                httpClientBuilder.setProxy(proxyHost);
            }
            httpClientBuilder.setRetryHandler(new DefaultHttpRequestRetryHandler(5, true));

            httpClient = httpClientBuilder.build();
        }
        return httpClient;
    }

    public void closeHttpClient() {
        try {
            if (httpClient != null) {
                httpClient.close();
                httpClient = null;
            }
        } catch (Exception e) {
            throw new RuntimeException("Unable to stop http client", e);
        }
    }

    public HttpClientHandler enableSLLConnectionSocketFactory() {
        try {
            // turns hostname verification off
            sslConnectionSocketFactory = new SSLConnectionSocketFactory(
                    new SSLContextBuilder().loadTrustMaterial(null, (TrustStrategy) (chain, authType) -> true).build(),
                    NoopHostnameVerifier.INSTANCE
            );
        } catch (Exception e) {
            throw new RuntimeException("Creating HttpClient is failed", e);
        }
        return this;
    }

    public HttpClientHandler enableRequestConfig(Duration duration) {
        requestConfig = RequestConfig.custom().
                setConnectTimeout((int) duration.toMillis()).
                setConnectionRequestTimeout((int) duration.toMillis()).
                setSocketTimeout((int) duration.toMillis()).build();
        return this;
    }

    public HttpClientHandler enableProxy(ProxyHostMachine proxyHostMachine) {
        proxyHost = new HttpHost(proxyHostMachine.getURIBuilder().getHost(),
                Integer.valueOf(proxyHostMachine.getPort()),
                proxyHostMachine.getURIBuilder().getScheme());
        return this;
    }

}

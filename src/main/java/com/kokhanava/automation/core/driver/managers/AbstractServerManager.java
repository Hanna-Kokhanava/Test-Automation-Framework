package com.kokhanava.automation.core.driver.managers;

import com.kokhanava.automation.core.logger.Logger;
import com.kokhanava.automation.core.tools.httpclients.HttpClient;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Created on 06.03.2019
 */
public class AbstractServerManager {

    protected static boolean checkStatus(URIBuilder uriBuilder) {
        boolean status = false;
        int tryCount = 3;

        do {
            HttpGet request = null;
            try {
                request = new HttpGet(uriBuilder.build());
            } catch (URISyntaxException e) {
                Logger.warn("Could not parse string as URI reference", e);
            }
            try (CloseableHttpResponse response = HttpClient.getHttpClient().execute(request)) {
                status = response.getStatusLine().getStatusCode() == HttpStatus.SC_OK;
            } catch (IOException e) {
                Logger.warn("Failed to get status of server", e);
            }
        } while (--tryCount > 0 && !status);

        return status;
    }

}

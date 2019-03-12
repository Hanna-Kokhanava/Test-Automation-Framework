package com.kokhanava.automation.core.driver.managers;

import com.kokhanava.automation.core.logger.Logger;
import com.kokhanava.automation.core.tools.HostMachine;
import com.kokhanava.automation.core.tools.httpclients.HttpClient;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.openqa.selenium.server.browserlaunchers.Sleeper;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.function.Function;

/**
 * Created on 06.03.2019
 */
public class AbstractServerManager {

    private static final int DEFAULT_SECONDS_COUNT_FOR_SERVER_CONDITION = 60;
    private static final int ATTEMPTS_COUNT = 3;

    /**
     * Waits for server starts on machine
     *
     * @param hostMachine       {@link HostMachine} instance
     * @param serverStatusCheck {@link Function} function for which status of server should be checked
     */
    protected static void waitForServerStarts(HostMachine hostMachine, Function<Void, Boolean> serverStatusCheck) {
        waitForServerCondition(serverStatusCheck);

        if (!serverStatusCheck.apply(null)) {
            throw new RuntimeException(String.format("Failed to start server at '%s'", hostMachine.getHostname()));
        }
        Logger.debug("Server was started successfully");
    }

    /**
     * Waits for server condition
     *
     * @param checkFunction {@link Function} which checks server condition
     */
    protected static void waitForServerCondition(Function<Void, Boolean> checkFunction) {
        Logger.debug("Waiting [" + DEFAULT_SECONDS_COUNT_FOR_SERVER_CONDITION + "] seconds for server condition");
        Calendar calendar = new GregorianCalendar();
        calendar.add(Calendar.SECOND, DEFAULT_SECONDS_COUNT_FOR_SERVER_CONDITION);

        while (!checkFunction.apply(null) && calendar.after(new GregorianCalendar())) {
            Sleeper.sleepTight(1000);
        }
    }

    /**
     * Checks server status
     *
     * @param uriBuilder {@link URIBuilder} instance
     * @return current status of server, running - true/false
     */
    protected static boolean checkStatus(URIBuilder uriBuilder) {
        int tryCount = ATTEMPTS_COUNT;
        boolean status;

        try {
            do {
                HttpGet request = new HttpGet(uriBuilder.build());
                try (CloseableHttpResponse response = HttpClient.getHttpClient().execute(request)) {
                    status = response.getStatusLine().getStatusCode() == HttpStatus.SC_OK;
                }
            } while (--tryCount > 0 && !status);

        } catch (Exception e) {
            status = false;
        }

        return status;
    }
}
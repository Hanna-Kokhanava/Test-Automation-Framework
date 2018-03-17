package com.linkedin.automation.core.appium;

import com.linkedin.automation.core.tools.http.HttpClientService;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.openqa.selenium.server.browserlaunchers.Sleeper;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.function.Function;

/**
 * Created on 15.03.2018
 */
public abstract class AbstractServer {
    private static final int WAIT_SECONDS_FOR_SERVER_CONDITION = Integer.parseInt("60");

    protected static boolean checkServerStatus(URIBuilder uriBuilder) {
        boolean status;
        int responseCode = 0;

        int attemptsCount = 3;
        try {
            do {
                //Get status information using GET
                HttpGet request = new HttpGet(uriBuilder.build());
                try (CloseableHttpResponse response = HttpClientService.getHttpClient().execute(request)) {
                    responseCode = response.getStatusLine().getStatusCode();
                    status = responseCode == HttpStatus.SC_OK;
                }
            } while (!status && --attemptsCount > 0);
        } catch (Exception e) {
            status = false;
        }
        System.out.println("Server returns status " + status + " with response code : " + responseCode);
        return status;
    }

    protected static void waitForServerStarts(Function<Void, Boolean> serverStatusFunction) {
        waitForServerCondition(serverStatusFunction);

        if (!serverStatusFunction.apply(null))
            throw new RuntimeException("Failed to start server at '%s'");
    }

    protected static void waitForServerCondition(Function<Void, Boolean> checkFunction) {
        Calendar calendar = new GregorianCalendar();
        calendar.add(Calendar.SECOND, WAIT_SECONDS_FOR_SERVER_CONDITION);
        while (!checkFunction.apply(null) && calendar.after(new GregorianCalendar())) {
            Sleeper.sleepTight(1);
        }
    }
}

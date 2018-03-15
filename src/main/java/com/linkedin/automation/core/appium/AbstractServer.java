package com.linkedin.automation.core.appium;

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

    protected static boolean checkStatus(URIBuilder uriBuilder) {
        boolean status = false;

        //TODO execute HttpGet request and get response status code
        //TODO try several times

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

package com.linkedin.automation.core.driver.managers.console;

import com.linkedin.automation.core.logger.Logger;
import com.linkedin.automation.core.tools.HostMachine;
import com.linkedin.automation.core.tools.files.PropertyLoader;
import com.linkedin.automation.core.tools.files.RemoteFileManager;
import com.linkedin.automation.core.tools.httpclients.HttpClient;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.openqa.selenium.server.browserlaunchers.Sleeper;

import java.io.File;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.function.Function;

import static com.linkedin.automation.core.tools.files.ResultFolder.ROOT_FOLDER;

public abstract class AbstractServerManager {

    private static final int WAIT_SECONDS_FOR_SERVER_CONDITION = Integer.parseInt(
            PropertyLoader.get(PropertyLoader.Property.WAIT_SECONDS_FOR_SERVER_CONDITION, "60"));

    /**
     * Wait for server starts on {@link HostMachine}
     *
     * @param hostMachine          on which {@link HostMachine} we need wait server
     * @param serverStatusFunction which {@link Function} we check status of server
     */
    protected static void waitForServerStarts(HostMachine hostMachine, Function<Void, Boolean> serverStatusFunction) {
        waitForServerCondition(serverStatusFunction);

        if (!serverStatusFunction.apply(null))
            throw new RuntimeException(String.format(
                    "Failed to start server at '%s'",
                    hostMachine.getHostname()));
        Logger.debug("Server started");
    }

    public static void waitForServerCondition(Function<Void, Boolean> checkFunction, int secondsForWait) {
        Logger.debug("Wait [" + secondsForWait + "] seconds for server condition");
        Calendar calendar = new GregorianCalendar();
        calendar.add(Calendar.SECOND, secondsForWait);
        while (!checkFunction.apply(null) && calendar.after(new GregorianCalendar())) {
            Sleeper.sleepTight(1);
        }
    }

    protected static void waitForServerCondition(Function<Void, Boolean> checkFunction) {
        waitForServerCondition(checkFunction, WAIT_SECONDS_FOR_SERVER_CONDITION);
    }

    protected static boolean checkStatus(URIBuilder uriBuilder) {
        boolean status;
        try {
            int tryCount = 3;
            do {
                HttpGet request = new HttpGet(uriBuilder.build());
                // Execute request
                try (CloseableHttpResponse response = HttpClient.getHttpClient().execute(request)) {
                    status = response.getStatusLine().getStatusCode() == HttpStatus.SC_OK;
                }
            } while (--tryCount > 0 && !status);

        } catch (Exception e) {
            status = false;
        }
        return status;
    }

    protected static void uploadSourceFiles(HostMachine host, File sourceFile) {
        if (host.isRemote()) {
            RemoteFileManager fileManager = new RemoteFileManager(host);
            fileManager.uploadDir(ROOT_FOLDER.getPathToFolder(host), sourceFile);
        }
    }
}

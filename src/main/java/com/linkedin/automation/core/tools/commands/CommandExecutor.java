package com.linkedin.automation.core.tools.commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created on 17.03.2018
 */
public class CommandExecutor {
    public static String execute(Command commandTemplate, Object... args) {
        String command = commandTemplate.getCommand(args);
        return execute(command);
    }

    public static String execute(String command) {
        return executeLocally(command);
    }

    private static String executeLocally(String command) {
        String commandOutput = null;
        StringBuilder buffer = new StringBuilder();
        String[] commands = new String[]{"cmd", "/c", command};

        try {
            Process process = Runtime.getRuntime().exec(commands);
            String line;
            BufferedReader is = new BufferedReader(new InputStreamReader(process.getInputStream()));
            while ((line = is.readLine()) != null) {
                buffer.append(line).append('\n');
            }

            commandOutput = buffer.toString().trim();
            is.close();
            process.destroy();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return commandOutput;
    }

    public static String getHostNameOfLocalhost() {
        return executeLocally(Command.SYSTEM_GET_HOST_NAME.getCommand());
    }

}

package com.linkedin.automation.core.tools.commands;

import com.linkedin.automation.core.tools.HostMachine;
import com.linkedin.automation.core.tools.OS;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

/**
 * Created on 17.03.2018
 */
public class CommandExecutor {
    private static final String OS_NAME_PROPERTY = "os.name";

    public static String execute(HostMachine machine, Command commandTemplate, Object... args) {
        String command = commandTemplate.getCommand(args);
        return execute(machine, command);
    }

    public static String execute(HostMachine machine, String command) {
        OS os = getOsOfMachine(machine);
        command = os != OS.WINDOWS
                ? Command.SYSTEM_SOURCE_ENVIRONMENT.getCommandTemplate(Objects.requireNonNull(os)) + " " + command
                : command;
        if (machine.isRemote()) {
            return executeRemotely(machine, command);
        }
        return executeLocally(os, command);
    }

    private static String executeLocally(OS os, String command) {
        String commandOutput = null;
        StringBuilder buffer = new StringBuilder();

        String[] commands = {command};
        if (os == OS.MAC)
            commands = new String[]{"bash", "-c", command};
        if (os == OS.WINDOWS)
            commands = new String[]{"cmd", "/c", command};

        try {

            Process p = Runtime.getRuntime().exec(commands);

            String line;
            BufferedReader is = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while ((line = is.readLine()) != null) {
                buffer.append(line).append('\n');
            }
            commandOutput = buffer.toString().trim();

            is.close();

            p.destroy();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return commandOutput;
    }

    private static OS getOsOfLocalMachine() {
        String result = System.getProperty(OS_NAME_PROPERTY).toLowerCase();
        if (result.contains(OS.WINDOWS.toString())) {
            return OS.WINDOWS;
        }
        if (result.contains(OS.MAC.toString())) {
            return OS.MAC;
        }
        return null;
    }

    public static String getHostNameOfLocalhost() {
        return executeLocally(getOsOfLocalMachine(), Command.SYSTEM_GET_HOST_NAME.getCommand());
    }

    public static OS getOsOfMachine(HostMachine machine) {
        String result;
        if (machine.isRemote()) {
            result = executeRemotely(machine, Command.SYSTEM_GET_OS_NAME.getCommandTemplate(OS.MAC)).toLowerCase();
            if (!result.contains(OS.MAC.toString()))
                result = executeRemotely(machine, Command.SYSTEM_GET_OS_NAME.getCommandTemplate(OS.WINDOWS)).toLowerCase();
            else return OS.MAC;
        } else result = System.getProperty(OS_NAME_PROPERTY).toLowerCase();

        if (result.contains(OS.WINDOWS.toString()))
            return OS.WINDOWS;
        if (result.contains(OS.MAC.toString()))
            return OS.MAC;
        return null;
    }

    //TODO need to implement
    private static String executeRemotely(HostMachine machine, String command) {
        String results = null;
//        try {
//            SSHManager ssh = new SSHManager(machine);
//            ssh.connect();
//            results = ssh.execute(command);
//            ssh.disconnect();
//        } catch (JSchException | IOException e) {
//            e.printStackTrace();
//        }
        return results;
    }

}

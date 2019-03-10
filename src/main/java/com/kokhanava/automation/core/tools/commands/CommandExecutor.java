package com.kokhanava.automation.core.tools.commands;

import com.google.common.util.concurrent.SimpleTimeLimiter;
import com.google.common.util.concurrent.TimeLimiter;
import com.kokhanava.automation.core.logger.Logger;
import com.kokhanava.automation.core.tools.HostMachine;
import com.kokhanava.automation.core.tools.OS;
import com.kokhanava.automation.core.tools.files.ResultFolder;

import javax.annotation.Nullable;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created on 17.03.2018
 */
public class CommandExecutor {
    private static final String OS_NAME_PROPERTY = "os.name";

    /**
     * Executes command with parameters on machine
     *
     * @param machine         the HostMachine
     * @param commandTemplate the command template
     * @param args            the parameters of command
     * @return result string
     */
    public static String execute(HostMachine machine, Command commandTemplate, Object... args) {
        String command = commandTemplate.getCommand(args);
        return execute(machine, command);
    }

    /**
     * Executes command on machine
     *
     * @param machine the HostMachine
     * @param command the command
     * @return command result string
     */
    public static String execute(HostMachine machine, String command) {
        OS os = getOsOfMachine(machine);
        if (os != OS.WINDOWS) {
            command = Command.SYSTEM_SOURCE_ENVIRONMENT.getCommandTemplate(os) + " " + command;
        }

        Logger.debug("Executing command [" + command + "] on " + machine.getHostname() + " host");
        String commandResult = machine.isRemote()
                ? executeRemotely(machine, command)
                : executeLocally(os, command);

        return Objects.requireNonNull(commandResult, "An error has occurred while command [" +
                command + "] execution on [" + machine.getHostname() + "] machine");
    }

    /**
     * Executes command locally
     *
     * @param os      the type OS of local machine
     * @param command the command
     * @return the string
     */
    @Nullable
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
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(p.getInputStream()));

            int timeout = 0;
            while (!bufferedReader.ready()) { //wait until Stream is ready to be read
                try {
                    Thread.sleep(10);
                    timeout++;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            timeout = 0;
            String line;
            while (timeout < 100) {
                while (bufferedReader.ready() && (line = bufferedReader.readLine()) != null) {
                    buffer.append(line).append(System.lineSeparator());
                }
            }
            commandOutput = buffer.toString().trim();
            bufferedReader.close();
            p.destroy();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return commandOutput;
    }

    /**
     * Executes command on machine from folder
     *
     * @param hostMachine the HostMachine
     * @param folder      from which folder execute
     * @param command     the command
     * @return result string
     */
    public static String executeCommandFromFolder(HostMachine hostMachine, ResultFolder folder, String command) {
        return executeCommandFromFolder(hostMachine, folder.getPathToFolder(hostMachine), command);
    }

    /**
     * Executes command on machine from folder
     *
     * @param hostMachine the HostMachine
     * @param folderPath  from which folder execute
     * @param command     the command
     * @return result string
     */
    public static String executeCommandFromFolder(HostMachine hostMachine, String folderPath, String command) {
        return execute(hostMachine, Command.CD.getCommand(hostMachine.getOs(), folderPath) + command);
    }

    /**
     * Get host name of the local host machine
     *
     * @return host name
     */
    public static String getHostNameOfLocalhost() {
        String localHostName = executeLocally(getOsOfLocalMachine(), Command.SYSTEM_GET_HOST_NAME.getCommand());
        return Objects.requireNonNull(localHostName, "An error has occurred while local host name obtaining");
    }

    /**
     * Get type of OS on machine
     *
     * @param machine {@link HostMachine} instance
     * @return type of {@link OS}
     */
    public static OS getOsOfMachine(HostMachine machine) {
        OS os;
        if (machine.isRemote()) {
            os = Arrays.stream(OS.values())
                    .filter(possibleOS ->
                            executeRemotely(machine, Command.SYSTEM_GET_OS_NAME.getCommandTemplate(possibleOS))
                                    .toLowerCase()
                                    .contains(possibleOS.toString()))
                    .findFirst()
                    .orElse(null);
        } else {
            os = getOsOfLocalMachine();
        }
        return Objects.requireNonNull(os, "Failed to define OS type of [" + machine.getHostname() + "] machine");
    }

    /**
     * Gets OS type (Windows, Mac, Linux) of local machine from system properties
     *
     * @return {@link OS} type
     */
    @Nullable
    private static OS getOsOfLocalMachine() {
        String osName = System.getProperty(OS_NAME_PROPERTY).toLowerCase();
        if (osName.contains(OS.WINDOWS.toString())) {
            return OS.WINDOWS;
        }
        if (osName.contains(OS.MAC.toString())) {
            return OS.MAC;
        }
        if (osName.contains(OS.LINUX.toString())) {
            return OS.LINUX;
        }
        return null;
    }

    //TODO need to implement when SSHManager will be fully implemented
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

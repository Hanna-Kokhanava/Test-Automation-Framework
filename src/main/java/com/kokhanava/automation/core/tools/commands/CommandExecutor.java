package com.kokhanava.automation.core.tools.commands;

import com.kokhanava.automation.core.logger.Logger;
import com.kokhanava.automation.core.tools.HostMachine;
import com.kokhanava.automation.core.tools.OS;
import com.kokhanava.automation.core.tools.files.ResultFolder;

import javax.annotation.Nullable;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

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
     * @return result string
     */
    public static String execute(HostMachine machine, String command) {
        Logger.debug("Executing command [" + command + "] on " + machine.getHostname() + " host");
        OS os = getOsOfMachine(machine);
        command = os != OS.WINDOWS
                ? Command.SYSTEM_SOURCE_ENVIRONMENT.getCommandTemplate(Objects.requireNonNull(os)) + " " + command
                : command;
        if (machine.isRemote()) {
            return executeRemotely(machine, command);
        }
        return executeLocally(os, command);
    }

    /**
     * Executes command locally
     *
     * @param os      the type OS of local machine
     * @param command the command
     * @return the string
     */
    private static String executeLocally(OS os, String command) {
        String commandOutput = null;
        StringBuilder buffer = new StringBuilder();

        String[] commands = {command};
        if (os == OS.MAC || os == OS.LINUX)
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
        return execute(hostMachine, Command.CD.getCommand(folderPath) + command);
    }

    public static String getHostNameOfLocalhost() {
        return executeLocally(getOsOfLocalMachine(), Command.SYSTEM_GET_HOST_NAME.getCommand());
    }

    /**
     * Get type of OS on machine
     *
     * @param machine {@link HostMachine} instance
     * @return type of {@link OS}
     */
    @Nullable
    public static OS getOsOfMachine(HostMachine machine) {
        if (machine.isRemote()) {
            String osName;
            for (OS possibleOS : OS.values()) {
                osName = executeRemotely(machine, Command.SYSTEM_GET_OS_NAME.getCommandTemplate(possibleOS)).toLowerCase();
                if (osName.contains(possibleOS.toString())) {
                    return possibleOS;
                }
            }
            return null;
        }
        return getOsOfLocalMachine();
    }

    /**
     * Gets OS type (Windows, Mac, Linux) of local machine from system properties
     *
     * @return {@link OS} type
     */
    @Nullable
    private static OS getOsOfLocalMachine() {
        String result = System.getProperty(OS_NAME_PROPERTY).toLowerCase();
        if (result.contains(OS.WINDOWS.toString())) {
            return OS.WINDOWS;
        }
        if (result.contains(OS.MAC.toString())) {
            return OS.MAC;
        }
        if (result.contains(OS.LINUX.toString())) {
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

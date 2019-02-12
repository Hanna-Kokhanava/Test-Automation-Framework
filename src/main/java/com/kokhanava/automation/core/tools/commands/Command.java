package com.kokhanava.automation.core.tools.commands;

import com.kokhanava.automation.core.tools.OS;

import java.util.Objects;

/**
 * Created on 17.03.2018
 */
public enum Command {
    SYSTEM_SOURCE_ENVIRONMENT("source ~/.bash_profile;"),
    SYSTEM_GET_HOST_NAME("hostname"),
    SYSTEM_GET_OS_NAME("sw_vers", "ver", "uname -a"),
    SYSTEM_TEMPLATE_KILL_PID("taskkill /PID %s /F"),

    ADB_DEVICES_UDID_LIST("adb devices | awk '!/grep/{print $1}' | grep -v 'List'"),
    IOS_IDEVICE_UDID_LIST("idevice_id --list"),

    UNZIP_FILE("unzip -u %s"),
    CD("cd %s; ");

    private String macCommand;
    private String winCommand;
    private String linuxCommand;
    private String command;

    Command(String command) {
        this.command = command;
    }

    //For Unix uses the same command as for mac
    Command(String macCommand, String winCommand) {
        this.macCommand = macCommand;
        this.winCommand = winCommand;
        this.linuxCommand = macCommand;
    }

    Command(String macCommand, String winCommand, String linuxCommand) {
        this.macCommand = macCommand;
        this.winCommand = winCommand;
        this.linuxCommand = linuxCommand;
    }

    public String getCommandTemplate(OS os) {
        switch (os) {
            case MAC:
                return getMacCommand();
            case WINDOWS:
                return getWinCommand();
            case LINUX:
                return getLinuxCommand();
            default:
                return command;
        }
    }

    public String getCommandTemplate() {
        return command;
    }

    public String getCommand(OS os, Object... arg) {
        return String.format(getCommandTemplate(os), arg);
    }

    public String getCommand(Object... arg) {
        return String.format(getCommandTemplate(), arg);
    }

    private String getMacCommand() {
        return Objects.nonNull(macCommand) ? macCommand : command;
    }

    private String getWinCommand() {
        return Objects.nonNull(winCommand) ? winCommand : command;
    }

    private String getLinuxCommand() {
        return Objects.nonNull(linuxCommand) ? linuxCommand : command;
    }

    @Override
    public String toString() {
        if (Objects.nonNull(command)) {
            return command;
        }
        if (Objects.nonNull(macCommand)) {
            return macCommand;
        }
        if (Objects.nonNull(linuxCommand)) {
            return linuxCommand;
        }
        return winCommand;
    }
}
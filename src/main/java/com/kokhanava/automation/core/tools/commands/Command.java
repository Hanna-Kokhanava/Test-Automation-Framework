package com.kokhanava.automation.core.tools.commands;

import com.kokhanava.automation.core.tools.OS;

import java.util.Objects;

/**
 * Created on 17.03.2018
 */
public enum Command {
    SYSTEM_SOURCE_ENVIRONMENT("source ~/.bash_profile;"),
    SYSTEM_GET_HOST_NAME("hostname"),
    SYSTEM_GET_OS_NAME("sw_vers", "ver"),
    SYSTEM_TEMPLATE_KILL_PID("taskkill /PID %s /F"),

    ADB_DEVICES_UDID_LIST("adb devices | awk '!/grep/{print $1}' | grep -v 'List'"),
    IOS_IDEVICE_UDID_LIST("idevice_id --list"),

    UNZIP_FILE("unzip -u %s"),
    CD("cd %s; ");

    private String macCommand;
    private String winCommand;
    private String command;

    Command(String command) {
        this.command = command;
    }

    Command(String macCommand, String winCommand) {
        this.winCommand = winCommand;
        this.macCommand = macCommand;
    }

    public String getCommandTemplate(OS os) {
        switch (os) {
            case MAC:
                return getMacCommand();
            case WINDOWS:
                return getWinCommand();
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
        if (Objects.isNull(macCommand)) {
            return command;
        }
        return macCommand;
    }

    private String getWinCommand() {
        if (Objects.isNull(winCommand)) {
            return command;
        }
        return winCommand;
    }

    @Override
    public String toString() {
        if (Objects.nonNull(command)) {
            return command;
        }
        if (Objects.nonNull(macCommand)) {
            return macCommand;
        }
        return winCommand;
    }
}
package com.kokhanava.automation.core.tools.commands;

import com.kokhanava.automation.core.tools.OS;

import java.util.Objects;

/**
 * Created on 17.03.2018
 */
public enum Command {
    // http://appium.io/docs/en/writing-running-appium/server-args/
    APPIUM_START_SERVER_ANDROID("appium --port %s -bp %s --log-timestamp --log appium%s.log --default-capabilities \"%s\""),
    APPIUM_GET_PID("for /f \"tokens=5\" %%i in ('netstat -aon ^| findstr %s ^| find \"LISTENING\"') do @echo %%i"),

    SYSTEM_SOURCE_ENVIRONMENT("source ~/.bash_profile;"),
    SYSTEM_GET_HOST_NAME("hostname"),
    SYSTEM_GET_OS_NAME("sw_vers", "ver", "uname -a"),
    SYSTEM_KILL_PROCESS_BY_PID("kill -9 %s", "taskkill /PID %s /F"),

    ADB_DEVICES_UDID_LIST("adb devices | awk '!/grep/{print $1}' | grep -v 'List'"),
    IOS_IDEVICE_UDID_LIST("idevice_id --list"),

    UNZIP_FILE("unzip -u %s"),
    CD("cd %s; ", "cd %s && ");

    private String macCommand;
    private String winCommand;
    private String linuxCommand;
    private String command;

    Command(String command) {
        this.command = command;
    }

    Command(String macCommand, String winCommand) {
        this.macCommand = macCommand;
        this.winCommand = winCommand;
        this.linuxCommand = macCommand; //For Unix uses the same command as for mac
    }

    Command(String macCommand, String winCommand, String linuxCommand) {
        this.macCommand = macCommand;
        this.winCommand = winCommand;
        this.linuxCommand = linuxCommand;
    }

    /**
     * Returns an appropriate command template depending on the {@link OS}
     *
     * @param os current {@link OS}
     * @return an appropriate command for OS
     */
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
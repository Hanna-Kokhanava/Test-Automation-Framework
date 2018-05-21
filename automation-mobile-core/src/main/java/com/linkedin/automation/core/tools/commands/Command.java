package com.linkedin.automation.core.tools.commands;

import com.linkedin.automation.core.tools.OS;

/**
 * Created on 17.03.2018
 */
public enum Command {
    APPIUM_TEMPLATE_START_APPIUM_SERVER_IOS("appium --port %s --log-timestamp --tmp %s --default-capabilities '%s'"
            , "start appium --port %s --log-timestamp --log appium%s.log"),
    APPIUM_TEMPLATE_START_APPIUM_SERVER_ANDROID("appium --port %s --log-timestamp -bp %s --default-capabilities '%s'"
            , "start appium --port %s --session-override -bp %s --log-timestamp --log appium%s.log"),
    APPIUM_TEMPLATE_GET_PID_APPIUM("ps -ef | grep appium | grep -w -e \"%s\" | awk '!/grep/{print $2}'",
            "FOR /F “tokens=5” %%i in ('netstat -ano ^| findstr %s’) do @echo %%i"),
    APPIUM_TEMPLATE_NOHUP("nohup %s &> appium%s.log 2>&1 &"),

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
        if (macCommand == null)
            return command;
        return macCommand;
    }

    private String getWinCommand() {
        if (winCommand == null)
            return command;
        return winCommand;
    }

    @Override
    public String toString() {
        if (command != null)
            return command;
        if (macCommand != null)
            return macCommand;
        return winCommand;
    }
}
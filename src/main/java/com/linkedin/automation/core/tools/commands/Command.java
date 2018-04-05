package com.linkedin.automation.core.tools.commands;

/**
 * Created on 17.03.2018
 */
public enum Command {
    //Only for Windows platform
    APPIUM_TEMPLATE_START_APPIUM_SERVER_ANDROID("cmd.exe /c start cmd.exe /k " +
            "appium -p %s --session-override -bp %s --log-timestamp --log appium%s.log"),
    APPIUM_TEMPLATE_NOHUP("nohup %s &> appium%s.log 2>&1 &"),

    SYSTEM_GET_HOST_NAME("hostname"),
    SYSTEM_TEMPLATE_KILL_PID("taskkill /PID %s /F"),

    CD("cd %s; ");

    private String command;

    Command(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }

    public String getCommand(Object... arg) {
        return String.format(getCommand(), arg);
    }
}

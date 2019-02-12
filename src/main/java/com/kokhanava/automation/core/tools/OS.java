package com.kokhanava.automation.core.tools;

/**
 * Created on 01.04.2018
 */
public enum OS {
    MAC("mac", '/'),

    LINUX("linux", '/'),

    WINDOWS("windows", '\\');

    private String os;
    private char fileSeparator;

    OS(String os, char fileSeparator) {
        this.os = os;
        this.fileSeparator = fileSeparator;
    }

    public char getFileSeparator() {
        return fileSeparator;
    }

    @Override
    public String toString() {
        return os;
    }
}

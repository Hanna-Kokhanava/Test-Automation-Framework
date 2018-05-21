package com.linkedin.automation.services.ui.handlers;

public interface IPageVerifier {
    /**
     * Checks that visibility of main components this page is correct
     *
     * @return is page loaded correct
     */
    boolean isPageLoaded();

    /**
     * Checks that page contains important components
     *
     * @return is page contain important components
     */
    boolean isPageLoadedCorrect();

}
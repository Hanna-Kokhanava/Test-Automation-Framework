package com.kokhanava.automation.core.page_elements.element;

public interface ICheckBox {

    /**
     * Executes action that lead to state isChecked = true
     */
    void check();

    /**
     * Executes action that lead to state isChecked = false
     */
    void uncheck();

    /**
     * Checks state of this component
     */
    boolean isChecked();

}

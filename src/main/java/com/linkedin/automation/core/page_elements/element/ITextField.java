package com.linkedin.automation.core.page_elements.element;

public interface ITextField extends ILabel {

    /**
     * Clears all the text entered into this text input.
     */
    void clear();

    /**
     * Prints specified text into this text input.
     *
     * @param text Text to print.
     */
    void setValue(String text);

    /**
     * Add focus to text field
     */
    void setFieldFocus();

    /**
     * Send specified char sequence
     *
     * @param keys text or key code sequence
     */
    void sendKeys(CharSequence... keys);

}

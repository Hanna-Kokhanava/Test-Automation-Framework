package com.linkedin.automation.core.device.functions;

/**
 * Created on 17.04.2018
 */
public enum Key {
    /* Android-specific control keys*/
    HOME(null, 3),
    BACK(null, 4),
    MENU(null, 82),

    /* IOS-specific control keys */
    GO("go", null),
    HIDE("Hide keyboard", null),
    NEXT("next", null),
    NUMBERS("more, numbers", null),
    SYMBOLS("more, symbols", null),
    LETTERS("more, letters", null),

    /* Common control keys */
    SHIFT("shift", 59),
    SHIFT_RIGHT("shift", 60),
    ENTER("return", 66),
    DELETE("delete", 67),
    SEARCH("Search", 84),

    SPACE("space", 62),

    MUTE(null, 164);

    /**
     * Android key code
     *
     * @see <a href=http://developer.android.com/reference/android/view/KeyEvent.html>KeyEvent</>
     */
    private Integer androidKeyCode;

    /**
     * IOS key name
     */
    private String iOSKeyName;

    Key(String ios, Integer android) {
        this.iOSKeyName = ios;
        this.androidKeyCode = android;
    }

    public Integer getAndroidCode() {
        return androidKeyCode;
    }

    public String getIOSName() {
        return iOSKeyName;
    }
}

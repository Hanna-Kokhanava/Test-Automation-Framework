package com.kokhanava.automation.core.device.functions;

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
    HIDE("hide keyboard", null),
    NEXT("next", null);

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

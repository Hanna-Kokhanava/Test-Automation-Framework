# Automated Testing Framework

## Installations
## Java JDK
* Download Java JDK from [Java JDK](http://www.oracle.com/technetwork/java/javase/downloads/index.html).
* Install it and set **JAVA_HOME** to JDK's bin folder.

## Android SDK tools
* Download SDK from [Android SDK](https://developer.android.com/studio/index.html).
* Run the *android* tool (included in the SDK/tools folder) and make sure an API Level 17 or greater SDK platform, Google Driver, SDK Tools and SDK platform-tools are installed.
* Set **ANDROID_HOME** variable to Android SDK path - "C:\Users\User\AppData\Local\Android\sdk" for Windows.
* Add the "...\sdk\platform-tools\" and "...\sdk\tools\" to PATH variable.

## For MacOS environmental variables will be in **.bash_profile** :
* export JAVA_HOME="$(/usr/libexec/java_home)"
* export ANDROID_HOME=/Users/hanna_kokhanava/Library/Android/sdk
* export PATH=${PATH}:$ANDROID_HOME/tools:$ANDROID_HOME/platform-tools:$JAVA_HOME$


## Tips
To get information about the name of the package and the first activity that has to be launched for the testing
1. Browse through the **SDK folder -> Build-Tools -> Version folder**
2. Open cmd and execute command **aapt dumb badging "path_to_apk"**
3. Find **package** (in the beginning of logs) and **launchable-activity** parameters

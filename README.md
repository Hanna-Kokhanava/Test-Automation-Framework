# Unified Test Automation Framework (UTAF)
The main purpose of this project is to combine base functionality implementation (driver creation, capabilities setting, etc) in one universal solution.

## Getting Started
### Installing
### Java JDK
* Download [Java JDK](http://www.oracle.com/technetwork/java/javase/downloads/index.html) and install it.

### Android SDK tools
* Download SDK from [Android SDK](https://developer.android.com/studio/index.html).
* Run the *android* tool (included in the SDK/tools folder) and make sure an API Level 17 or greater SDK platform, Google Driver, SDK Tools and SDK platform-tools are installed.

#### Set environment variables :
**For Windows** -> System properties
* Set **JAVA** system env. variable to JDK's **bin** folder
* Set **JAVA_HOME** system env. variable to **JDK** folder
* Add __%JAVA_HOME%\bin__ to **PATH** variable.

* Set **ANDROID_HOME** system env. variable to **Android SDK** folder (e.g. "C:\Users\User\AppData\Local\Android\sdk")
* Add the __"...\sdk\platform-tools\"__ and __"...\sdk\tools\"__ to the **PATH** variable.

**For MacOS** -> ~/.bash_profile :
```
export JAVA_HOME="$(/usr/libexec/java_home)"
export ANDROID_HOME=/Users/hanna_kokhanava/Library/Android/sdk
export PATH=${PATH}:$ANDROID_HOME/tools:$ANDROID_HOME/platform-tools:$JAVA_HOME$
```

### Git
Download and install [Git](https://git-scm.com/download)

### Node
Download and install [NodeJS](https://nodejs.org/en/download/)

### Appium
  Download Appium using one of the following options:
* Latest version : 
```
npm install -g appium
```
* Specific version : 
```
npm install -g appium@1.11.0
```

### Appium Desktop
Download and install [Appium](https://github.com/appium/appium-desktop/releases)

### IntelliJ IDEA
Download and install [Community Edition](https://www.jetbrains.com/idea/download/#section=windows)

### Genymotion
* Download and install [Genymotion](https://www.genymotion.com/fun-zone/)

DHCP problem - https://www.genymotion.com/help/desktop/faq/#category-virtualbox

INSTALL_FAILED_NO_MATCHING_ABIS - http://aksahu.blogspot.com/2016/09/how-to-fix-installfailednomatchingabis.html

## Tips
To get information about the name of the package and the first activity that has to be launched for the testing
1. Browse through the **SDK folder -> Build-Tools -> Version folder**
2. Open cmd and execute command **./aapt dumb badging "path_to_apk"**
3. Find **package** (in the beginning of logs) and **launchable-activity** parameters

## Links
Appium server arguments - [Appium CLI](http://appium.io/docs/en/writing-running-appium/server-args/)

Default capabilities JSON - [--default-capabilities](https://github.com/appium/appium/blob/master/docs/en/writing-running-appium/default-capabilities-arg.md)


## Authors
**[Hanna Kokhanava](https://github.com/Hanna-Kokhanava)** - God of the UTAF

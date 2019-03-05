# Unified Test Automation Framework (UTAF)
The main purpose of this project is to combine base functionality implementation (driver creation, capabilities setting, etc) in one universal solution.

## Getting Started
### Installing
### Java JDK
1. Download [Java JDK](http://www.oracle.com/technetwork/java/javase/downloads/index.html) and install it.
2. Set environment variables.

### Android SDK tools
1. Download SDK from [Android SDK](https://developer.android.com/studio/index.html).
2. Run the *android* tool (included in the SDK/tools folder) and make sure an API Level 17 or greater SDK platform, Google Driver, SDK Tools and SDK platform-tools are installed.
3. Set environment variables.

#### Set environment variables :
**For Windows** -> System properties
* Set **JAVA** env. variable to JDK's **bin** folder
* Set **JAVA_HOME** env. variable to **JDK** folder
* Add __%JAVA_HOME%\bin__ to **PATH** variable.
* Set **ANDROID_HOME** env. variable to **Android SDK** folder (e.g. "C:\Users\User\AppData\Local\Android\sdk")
* Add the __"...\sdk\platform-tools\"__ and __"...\sdk\tools\"__ to **PATH** variable.

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


## Tips
To get information about the name of the package and the first activity that has to be launched for the testing
1. Browse through the **SDK folder -> Build-Tools -> Version folder**
2. Open cmd and execute command **./aapt dumb badging "path_to_apk"**
3. Find **package** (in the beginning of logs) and **launchable-activity** parameters


## Appium installation errors
### **Proxy issue** SELF_SIGNED_CERT_IN_CHAIN request to https://registry.npmjs.org/appium failed, reason: self signed certificate in certificate chain
**Solution** 

npm config set registry http://registry.npmjs.org/ (not recommended?)

### **Chromedriver installation proxy issue**
**Solution**

Install from mirror - add **chromedriver_cdnurl=http://npm.taobao.org/mirrors/chromedriver** property to the  **..\nodejs\node_modules\npm\npmrc** file

### **Downloading Python failed - self signed certificate in certificate chain** 
**Solution**

* Install [Python](https://www.python.org/downloads/) manually 
* Set path (Windows) - path %path%;C:\Python


## Authors
**[Hanna Kokhanava](https://github.com/Hanna-Kokhanava)** - God of the UTAF

package com.linkedin.automation.core.matchers;

import com.linkedin.automation.core.device.Device;
import com.linkedin.automation.core.device.DeviceManager;
import org.hamcrest.Description;

public class WhenSelfMatchers {

    private static final class WhenDevice extends SelfMatcher {
        private final Device.DeviceType[] devices;
        private final SelfMatcher matcher;
        private final SelfMatcher elseMatcher;

        private WhenDevice(Device.DeviceType[] device, SelfMatcher matcher, SelfMatcher elseMatcher) {
            this.devices = device;
            this.matcher = matcher;
            this.elseMatcher = elseMatcher;
        }

        private WhenDevice(Device.DeviceType[] device, SelfMatcher matcher) {
            this(device, matcher, CommonMatchers.alwaysTrue());
        }

        private WhenDevice(Device.DeviceType device, SelfMatcher matcher, SelfMatcher elseMatcher) {
            this(new Device.DeviceType[]{device}, matcher, elseMatcher);
        }

        private WhenDevice(Device.DeviceType device, SelfMatcher matcher) {
            this(device, matcher, CommonMatchers.alwaysTrue());
        }

        private boolean foundMatchedDevice() {
            Device currentDevice = DeviceManager.getCurrentDevice();
            boolean foundMatchedDevice = false;

            for (Device.DeviceType device : devices)
                foundMatchedDevice |= currentDevice.getDeviceType().is(device);
            return foundMatchedDevice;
        }

        @Override
        protected boolean check(Description mismatchDescription, boolean doAppend) {
            if (foundMatchedDevice()) {
                return matcher.logCheck(mismatchDescription, doAppend);
            } else {
                return elseMatcher.logCheck(mismatchDescription, doAppend);
            }
        }

        @Override
        public void describeTo(Description description) {
            if (foundMatchedDevice())
                description
                        .appendDescriptionOf(matcher)
                        .appendText(" [as device is of ")
                        .appendValue(devices)
                        .appendText("]");
            else
                description
                        .appendDescriptionOf(elseMatcher)
                        .appendText(" [as device is not of ")
                        .appendValue(devices)
                        .appendText("]");
        }
    }

    private static final class WhenNotDevice extends SelfMatcher {
        private final Device.DeviceType[] devices;
        private final SelfMatcher matcher;
        private final SelfMatcher elseMatcher;

        private WhenNotDevice(Device.DeviceType[] devices, SelfMatcher matcher) {
            this(devices, matcher, CommonMatchers.alwaysTrue());
        }

        private WhenNotDevice(Device.DeviceType[] devices, SelfMatcher matcher, SelfMatcher elseMatcher) {
            this.devices = devices;
            this.matcher = matcher;
            this.elseMatcher = elseMatcher;
        }

        private WhenNotDevice(Device.DeviceType device, SelfMatcher matcher, SelfMatcher elseMatcher) {
            this(new Device.DeviceType[]{device}, matcher, elseMatcher);
        }

        private WhenNotDevice(Device.DeviceType device, SelfMatcher matcher) {
            this(device, matcher, CommonMatchers.alwaysTrue());
        }

        private boolean foundMatchedDevice() {
            Device currentDevice = DeviceManager.getCurrentDevice();
            boolean foundMatchedDevice = false;

            for (Device.DeviceType device : devices)
                foundMatchedDevice |= currentDevice.getDeviceType().is(device);
            return foundMatchedDevice;
        }

        @Override
        protected boolean check(Description mismatchDescription, boolean doAppend) {
            if (!foundMatchedDevice()) {
                return matcher.logCheck(mismatchDescription, doAppend);
            } else {
                return elseMatcher.logCheck(mismatchDescription, doAppend);
            }
        }

        @Override
        public void describeTo(Description description) {
            if (!foundMatchedDevice())
                description
                        .appendDescriptionOf(matcher)
                        .appendText(" [as device is not of ")
                        .appendValue(devices)
                        .appendText("]");
            else
                description
                        .appendDescriptionOf(elseMatcher)
                        .appendText(" [as device is of ")
                        .appendValue(devices)
                        .appendText("]");
        }
    }

    private static final class WhenMatcher extends SelfMatcher {
        private final SelfMatcher condition;
        private final SelfMatcher matcher;
        private final SelfMatcher elseMatcher;

        private WhenMatcher(SelfMatcher condition, SelfMatcher matcher, SelfMatcher elseMatcher) {
            this.condition = condition;
            this.matcher = matcher;
            this.elseMatcher = elseMatcher;
        }

        private WhenMatcher(SelfMatcher condition, SelfMatcher matcher) {
            this(condition, matcher, CommonMatchers.alwaysTrue());
        }

        @Override
        protected boolean check(Description mismatchDescription, boolean doAppend) {
            if (condition.check()) {
                return matcher.logCheck(mismatchDescription, doAppend);
            } else {
                return elseMatcher.logCheck(mismatchDescription, doAppend);
            }
        }

        @Override
        public void describeTo(Description description) {
            if (condition.check(false))
                description
                        .appendDescriptionOf(matcher)
                        .appendText(" [as ")
                        .appendDescriptionOf(condition)
                        .appendText("]");
            else
                description
                        .appendDescriptionOf(elseMatcher)
                        .appendText(" [as not ")
                        .appendDescriptionOf(condition)
                        .appendText("]");
        }

    }

    /**
     * Creates a matcher that evaluates the given matcher if device is of specified type, otherwise
     * always <code>true</code>
     */
    public static SelfMatcher when(Device.DeviceType device, SelfMatcher matcher) {
        return new WhenDevice(device, matcher);
    }

    /**
     * Creates a matcher that evaluates the given <code>matcher</code> if device is of specified
     * type, otherwise evaluates <code>elseMatcher</code>
     */
    public static SelfMatcher when(Device.DeviceType device, SelfMatcher matcher, SelfMatcher elseMatcher) {
        return new WhenDevice(device, matcher, elseMatcher);
    }

    /**
     * Creates a matcher that evaluates the given matcher if device is any of specified types, otherwise
     * always <code>true</code>
     */
    public static SelfMatcher whenAnyOf(Device.DeviceType[] devices, SelfMatcher matcher) {
        return new WhenDevice(devices, matcher);
    }

    /**
     * Creates a matcher that evaluates the given <code>matcher</code> if device is any of specified
     * types, otherwise evaluates <code>elseMatcher</code>
     */
    public static SelfMatcher whenAnyOf(Device.DeviceType[] devices, SelfMatcher matcher, SelfMatcher elseMatcher) {
        return new WhenDevice(devices, matcher, elseMatcher);
    }

    /**
     * Creates a matcher that evaluates the given matcher if device is not of specified type,
     * otherwise always <code>true</code>
     */
    public static SelfMatcher whenNot(Device.DeviceType device, SelfMatcher matcher) {
        return new WhenNotDevice(device, matcher);
    }

    /**
     * Creates a matcher that evaluates the given <code>matcher</code> if device is not of specified
     * type, otherwise evaluates <code>elseMatcher</code>
     */
    public static SelfMatcher whenNot(Device.DeviceType device, SelfMatcher matcher, SelfMatcher elseMatcher) {
        return new WhenNotDevice(device, matcher, elseMatcher);
    }

    /**
     * Creates a matcher that evaluates the given matcher if device is not any of specified types,
     * otherwise always <code>true</code>
     */
    public static SelfMatcher whenNotAnyOf(Device.DeviceType[] devices, SelfMatcher matcher) {
        return new WhenNotDevice(devices, matcher);
    }

    /**
     * Creates a matcher that evaluates the given <code>matcher</code> if device is not any of specified
     * types, otherwise evaluates <code>elseMatcher</code>
     */
    public static SelfMatcher whenNotAnyOf(Device.DeviceType[] devices, SelfMatcher matcher, SelfMatcher elseMatcher) {
        return new WhenNotDevice(devices, matcher, elseMatcher);
    }

    /**
     * Creates a matcher that evaluates the given <code>matcher</code> if <code>condition</code>
     * matcher evauates to <code>true</code>, otherwise always <code>true</code>
     */
    public static SelfMatcher when(SelfMatcher condition, SelfMatcher matcher) {
        return new WhenMatcher(condition, matcher);
    }

    /**
     * Creates a matcher that evaluates the given <code>matcher</code> if <code>condition</code>
     * matcher evauates to <code>true</code>, otherwise evaluates <code>elseMatcher</code>
     */
    public static SelfMatcher when(SelfMatcher condition, SelfMatcher matcher, SelfMatcher elseMatcher) {
        return new WhenMatcher(condition, matcher, elseMatcher);
    }

}

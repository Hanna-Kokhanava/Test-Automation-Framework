package com.linkedin.automation.pages.resources;

public class NavigationItems {

    public enum BarItem implements NavigationItem {
        SEARCH("SEARCH"),
        HOME_APP("HOME_APP"),
        PROFILE(""),

        HOME("Home"),
        NETWORK("My network"),
        MESSAGING("Messaging"),
        NOTIFICATIONS("Notifications"),
        JOBS("Jobs");

        private final String itemName;

        BarItem(String itemName) {
            this.itemName = itemName;
        }

        @Override
        public String getName() {
            return itemName;
        }

        @Override
        public String toString() {
            return itemName;
        }
    }
}

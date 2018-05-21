package com.linkedin.automation.pages.resources;

public class NavigationItems {

    public enum BarItem implements NavigationItem {
        SEARCH("SEARCH"),
        MENU("MENU"),
        BACK("BACK"),
        LOGOUT("LOGOUT"),

        HOME("Feed"),
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

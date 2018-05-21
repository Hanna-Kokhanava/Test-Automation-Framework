package com.linkedin.automation.core.matchers;

import com.linkedin.automation.page_elements.element.ILabel;
import com.linkedin.automation.page_elements.interfaces.Availability;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.internal.WrapsElement;

import static org.hamcrest.Matchers.is;

public final class SelfElementMatchers {

    private static final class HasAttribute extends SelfMatcher {
        private final WrapsElement wrapsElement;
        private final Matcher<String> valueMatcher;
        private final String attribute;

        private HasAttribute(WrapsElement wrapsElement, Matcher<String> valueMatcher, String attribute) {
            this.wrapsElement = wrapsElement;
            this.valueMatcher = valueMatcher;
            this.attribute = attribute;
        }

        @Override
        protected boolean check(Description mismatchDescription, boolean doAppend) {
            String attribute2 = wrapsElement.getWrappedElement().getAttribute(attribute);
            boolean matched = valueMatcher.matches(attribute2);
            if (!matched && doAppend) {
                mismatchDescription
                        .appendText("attribute ")
                        .appendValue(attribute)
                        .appendText(" of element ")
                        .appendValue(wrapsElement.getWrappedElement())
                        .appendText(" was ")
                        .appendValue(attribute2)
                        .appendText(", ");
            }
            return matched;
        }

        @Override
        public void describeTo(Description description) {
            description
                    .appendValue(wrapsElement.getWrappedElement())
                    .appendText(" attribute ")
                    .appendValue(attribute)
                    .appendText(" ")
                    .appendDescriptionOf(valueMatcher);
        }
    }

    private static final class HasHeight extends FeatureMatcher<WrapsElement, Integer> {
        private HasHeight(WrapsElement element, Matcher<Integer> heightMatcher) {
            super(element, heightMatcher, "element height", "height of element");
        }

        @Override
        protected Integer featureValueOf(WrapsElement actual) {
            return actual.getWrappedElement().getSize().getHeight();
        }
    }

    private static final class HasLocation extends SelfMatcher {
        private final WrapsElement wrapsElement;
        private final Matcher<Integer> xMatcher;
        private final Matcher<Integer> yMatcher;

        private HasLocation(WrapsElement wrapsElement, Matcher<Integer> xMatcher, Matcher<Integer> yMatcher) {
            this.wrapsElement = wrapsElement;
            this.xMatcher = xMatcher;
            this.yMatcher = yMatcher;
        }

        @Override
        protected boolean check(Description mismatchDescription, boolean doAppend) {
            Point location = wrapsElement.getWrappedElement().getLocation();
            boolean hasLocation = xMatcher.matches(location.getX())
                    && yMatcher.matches(location.getY());
            if (!hasLocation && doAppend) {
                mismatchDescription
                        .appendText("location of element ")
                        .appendValue(wrapsElement.getWrappedElement())
                        .appendText(" was ")
                        .appendValue(location)
                        .appendText(", ");
            }
            return hasLocation;
        }

        @Override
        public void describeTo(Description description) {
            description
                    .appendValue(wrapsElement.getWrappedElement())
                    .appendText(" element X-location ")
                    .appendDescriptionOf(xMatcher)
                    .appendText(" and Y-location ")
                    .appendDescriptionOf(yMatcher);
        }
    }

    private static final class HasSize extends SelfMatcher {
        private final WrapsElement wrapsElement;
        private final Matcher<Integer> widthMatcher;
        private final Matcher<Integer> heightMatcher;

        private HasSize(WrapsElement wrapsElement, Matcher<Integer> widthMatcher, Matcher<Integer> heightMatcher) {
            this.wrapsElement = wrapsElement;
            this.widthMatcher = widthMatcher;
            this.heightMatcher = heightMatcher;
        }

        @Override
        protected boolean check(Description mismatchDescription, boolean doAppend) {
            Dimension size = wrapsElement.getWrappedElement().getSize();
            boolean hasSize = widthMatcher.matches(size.getWidth())
                    && heightMatcher.matches(size.getHeight());
            if (!hasSize && doAppend) {
                mismatchDescription
                        .appendText("size of element ")
                        .appendValue(wrapsElement.getWrappedElement())
                        .appendText(" was ")
                        .appendValue(size)
                        .appendText(", ");
            }
            return hasSize;
        }

        @Override
        public void describeTo(Description description) {
            description
                    .appendValue(wrapsElement.getWrappedElement())
                    .appendText(" element width ")
                    .appendDescriptionOf(widthMatcher)
                    .appendText(" and height ")
                    .appendDescriptionOf(heightMatcher);
        }
    }

    private static final class HasText extends FeatureMatcher<ILabel, String> {
        private HasText(ILabel labelElement, Matcher<String> textMatcher) {
            super(labelElement, textMatcher, "element text", "text of element");
        }

        @Override
        protected String featureValueOf(ILabel actual) {
            return actual.getText();
        }
    }

    private static final class HasWidth extends FeatureMatcher<WrapsElement, Integer> {
        private HasWidth(WrapsElement wrapsElement, Matcher<Integer> widthMatcher) {
            super(wrapsElement, widthMatcher, "element width", "width of element");
        }

        @Override
        protected Integer featureValueOf(WrapsElement actual) {
            return actual.getWrappedElement().getSize().getWidth();
        }
    }

    private static final class HasXLocation extends FeatureMatcher<WrapsElement, Integer> {
        private HasXLocation(WrapsElement wrapsElement, Matcher<Integer> xMatcher) {
            super(wrapsElement, xMatcher, "element X-location", "X-location of element");
        }

        @Override
        protected Integer featureValueOf(WrapsElement actual) {
            return actual.getWrappedElement().getLocation().getX();
        }
    }

    private static final class HasYLocation extends FeatureMatcher<WrapsElement, Integer> {
        private HasYLocation(WrapsElement wrapsElement, Matcher<Integer> yMatcher) {
            super(wrapsElement, yMatcher, "element Y-location", "Y-location of element");
        }

        @Override
        protected Integer featureValueOf(WrapsElement actual) {
            return actual.getWrappedElement().getLocation().getY();
        }
    }

    private static final class IsDisplayed extends SelfMatcher {
        private final Availability availabilityElement;

        private IsDisplayed(Availability availabilityElement) {
            this.availabilityElement = availabilityElement;
        }

        @Override
        protected boolean check(Description mismatchDescription, boolean doAppend) {
            boolean displayed = availabilityElement.isDisplayed();
            if (!displayed && doAppend) {
                mismatchDescription
                        .appendText(" element ")
                        .appendValue(availabilityElement)
                        .appendText(" is not displayed")
                        .appendText(", ");
            }
            return displayed;
        }

        @Override
        public void describeTo(Description description) {
            description
                    .appendValue(availabilityElement)
                    .appendText(" element displayed");
        }
    }

    private static final class IsNotDisplayed extends SelfMatcher {
        private final Availability availabilityElement;

        private IsNotDisplayed(Availability availabilityElement) {
            this.availabilityElement = availabilityElement;
        }

        @Override
        protected boolean check(Description mismatchDescription, boolean doAppend) {
            boolean displayed = availabilityElement.isDisplayed();
            if (displayed && doAppend) {
                mismatchDescription
                        .appendText(" element ")
                        .appendValue(availabilityElement)
                        .appendText(" is displayed")
                        .appendText(", ");
            }
            return !displayed;
        }

        @Override
        public void describeTo(Description description) {
            description
                    .appendValue(availabilityElement)
                    .appendText(" element is not displayed");
        }
    }

    private static final class IsExists extends SelfMatcher {
        private final Availability availabilityElement;

        private IsExists(Availability availabilityElement) {
            this.availabilityElement = availabilityElement;
        }

        @Override
        protected boolean check(Description mismatchDescription, boolean doAppend) {
            boolean exists = availabilityElement.isExist();
            if (!exists && doAppend) {
                mismatchDescription
                        .appendText(" element ")
                        .appendValue(availabilityElement)
                        .appendText(" does not exist")
                        .appendText(", ");
            }
            return exists;
        }

        @Override
        public void describeTo(Description description) {
            description
                    .appendValue(availabilityElement)
                    .appendText(" element exists");
        }
    }

    private static final class IsNotExists extends SelfMatcher {
        private final Availability availabilityElement;

        private IsNotExists(Availability availabilityElement) {
            this.availabilityElement = availabilityElement;
        }

        @Override
        protected boolean check(Description mismatchDescription, boolean doAppend) {
            boolean exists = availabilityElement.isExist();
            if (exists && doAppend) {
                mismatchDescription
                        .appendText(" element ")
                        .appendValue(availabilityElement)
                        .appendText(" is exist")
                        .appendText(", ");
            }
            return !exists;
        }

        @Override
        public void describeTo(Description description) {
            description
                    .appendValue(availabilityElement)
                    .appendText(" does not exist");
        }
    }
    private static final class IsSelected extends SelfMatcher {
        private final WrapsElement wrapsElement;

        private IsSelected(WrapsElement wrapsElement) {
            this.wrapsElement = wrapsElement;
        }

        @Override
        protected boolean check(Description mismatchDescription, boolean doAppend) {
            boolean selected = wrapsElement.getWrappedElement().isSelected();
            if (!selected && doAppend) {
                mismatchDescription
                        .appendText(" element ")
                        .appendValue(wrapsElement.getWrappedElement())
                        .appendText(" is not selected")
                        .appendText(", ");
            }
            return selected;
        }

        @Override
        public void describeTo(Description description) {
            description
                    .appendValue(wrapsElement.getWrappedElement())
                    .appendText(" element selected");
        }
    }

    /**
     * Creates matcher that checks if element is currently displayed on page.
     */
    public static SelfMatcher displayed(Availability element) {
        return new IsDisplayed(element);
    }

    /**
     * Creates matcher that checks if element is currently not displayed on page.
     */
    public static SelfMatcher notDisplayed(Availability element) {
        return new IsNotDisplayed(element);
    }

    /**
     * Creates matcher that checks if element currently exists on page.
     */
    public static SelfMatcher exists(Availability element) {
        return new IsExists(element);
    }

    /**
     * Creates matcher that checks if element currently not exists on page.
     */
    public static SelfMatcher notExists(Availability element) {
        return new IsNotExists(element);
    }

    /**
     * Creates matcher that matches value of specified attribute with the given matcher.
     *
     * @param attribute    Name of matched attribute.
     * @param valueMatcher Matcher to match attribute value with.
     */
    public static SelfMatcher hasAttribute(
            WrapsElement element, String attribute, Matcher<String> valueMatcher) {
        return new HasAttribute(element, valueMatcher, attribute);
    }

    /**
     * Creates matcher that checks if value of specified element attribute equals to the given
     * value.
     *
     * @param attribute Name of matched attribute.
     * @param value     Expected attribute value.
     */
    public static SelfMatcher hasAttribute(
            WrapsElement element, String attribute, String value) {
        return new HasAttribute(element, is(value), attribute);
    }

    /**
     * Creates matcher that matches element height with given height matcher
     *
     * @param heightMatcher Matcher to match element height with
     */
    public static SelfMatcher hasHeight(
            WrapsElement element, Matcher<Integer> heightMatcher) {
        return new HasHeight(element, heightMatcher);
    }

    /**
     * Creates matcher that matches element location with given X-location & Y-location matchers.
     *
     * @param xMatcher Matcher to match element X-location with
     * @param yMatcher Matcher to match element Y-location with
     */
    public static SelfMatcher hasLocation(
            WrapsElement element, Matcher<Integer> xMatcher, Matcher<Integer> yMatcher) {
        return new HasLocation(element, xMatcher, yMatcher);
    }

    /**
     * Creates matcher that matches element size with given width & height matchers.
     *
     * @param widthMatcher  Matcher to match element width with
     * @param heightMatcher Matcher to match element height with
     */
    public static SelfMatcher hasSize(
            WrapsElement element, Matcher<Integer> widthMatcher, Matcher<Integer> heightMatcher) {
        return new HasSize(element, widthMatcher, heightMatcher);
    }

    /**
     * Creates matcher that matches element text with given matcher.
     *
     * @param textMatcher Matcher to match element text with.
     */
    public static SelfMatcher hasText(
            ILabel element, Matcher<String> textMatcher) {
        return new HasText(element, textMatcher);
    }

    /**
     * Creates matcher that checks if element text equals to the given text.
     *
     * @param text Expected text of element.
     */
    public static SelfMatcher hasText(
            ILabel element, String text) {
        return new HasText(element, is(text));
    }

    /**
     * Creates matcher that matches element width with given width matcher
     *
     * @param widthMatcher Matcher to match element width with
     */
    public static SelfMatcher hasWidth(
            WrapsElement element, Matcher<Integer> widthMatcher) {
        return new HasWidth(element, widthMatcher);
    }

    /**
     * Creates matcher that matches element X-location with given X-location matcher
     *
     * @param xMatcher Matcher to match element X-location with
     */
    public static SelfMatcher hasXLocation(
            WrapsElement element, Matcher<Integer> xMatcher) {
        return new HasXLocation(element, xMatcher);
    }

    /**
     * Creates matcher that matches element Y-location with given Y-location matcher
     *
     * @param yMatcher Matcher to match element Y-location with
     */
    public static SelfMatcher hasYLocation(
            WrapsElement element, Matcher<Integer> yMatcher) {
        return new HasYLocation(element, yMatcher);
    }

    /**
     * Creates matcher that checks if element is currently selected.
     */
    public static SelfMatcher selected(WrapsElement wrapsElement) {
        return new IsSelected(wrapsElement);
    }

}

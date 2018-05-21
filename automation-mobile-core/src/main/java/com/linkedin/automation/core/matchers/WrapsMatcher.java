package com.linkedin.automation.core.matchers;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

/**
 * Class representing <code>SelfMatcher</code> that wraps underlying <i>Hamcrest</i> {@link Matcher}
 */
public class WrapsMatcher<T> extends SelfMatcher {
    private final T input;
    private final Matcher<? super T> matcher;

    private WrapsMatcher(T input, Matcher<? super T> matcher) {
        this.matcher = matcher;
        this.input = input;
    }

    @Override
    protected boolean check(Description mismatchDescription, boolean doAppend) {
        boolean matched = matcher.matches(input);
        if (!matched && doAppend) {
            matcher.describeMismatch(input, mismatchDescription);
            mismatchDescription.appendText(", ");
        }
        return matched;
    }

    @Override
    public void describeTo(Description description) {
        description
                .appendValue(input)
                .appendText(" ")
                .appendDescriptionOf(matcher);
    }

    /**
     * Creates a matcher that matches if the examined T input matches to specified hamcrest
     * <code>wrappedMatcher</code>
     */
    public static <T> SelfMatcher wrap(T input, Matcher<T> wrappedMatcher) {
        return new WrapsMatcher<>(input, wrappedMatcher);
    }
}

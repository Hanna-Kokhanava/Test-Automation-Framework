package com.linkedin.automation.core.matchers;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

/**
 * Supporting class for matching a feature of an object. Decorates <i>Hamcrest</i> {@link Matcher},
 * retain its behaviour and apply at specific feature of input object. Implement
 * <code>featureValueOf()</code> in a subclass to pull out the feature to be matched against.
 */
public abstract class FeatureMatcher<T, U> extends SelfMatcher {
    private final T input;
    private final Matcher<? super U> matcher;
    private final String featureDescription;
    private final String featureMismatchDesc;

    public FeatureMatcher(T input, Matcher<? super U> matcher, String featureDescription, String featureMismatchDesc) {
        this.matcher = matcher;
        this.input = input;
        this.featureDescription = featureDescription;
        this.featureMismatchDesc = featureMismatchDesc;
    }

    /**
     * Implement this to extract the interesting feature.
     *
     * @param actual the target object
     * @return the feature to be matched
     */
    protected abstract U featureValueOf(T actual);

    @Override
    protected boolean check(Description mismatchDescription, boolean doAppend) {
        final U featureValue = featureValueOf(input);
        boolean matches = matcher.matches(featureValue);
        if (!matches && doAppend) {
            mismatchDescription
                    .appendText(featureMismatchDesc)
                    .appendText(" ")
                    .appendValue(input)
                    .appendText(" ");
            matcher.describeMismatch(featureValue, mismatchDescription);
            mismatchDescription.appendText(", ");
        }
        return matches;
    }

    @Override
    public void describeTo(Description description) {
        description
                .appendValue(input)
                .appendText(" ")
                .appendText(featureDescription)
                .appendText(" ")
                .appendDescriptionOf(matcher);
    }
}
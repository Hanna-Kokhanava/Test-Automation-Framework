package com.linkedin.automation.core.matchers;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;

import static com.linkedin.automation.core.matchers.WrapsMatcher.wrap;

/**
 * Class contains static methods that wrap any of existing <i>Hamcrest</i> {@link Matcher}s
 */
public class WrapsMatchers {

    /**
     * @see Matchers#is(Matcher)
     */
    public static <T> SelfMatcher is(T input, Matcher<T> matcher) {
        return wrap(input, Matchers.is(matcher));
    }

    /**
     * @see Matchers#is(Object)
     */
    public static <T> SelfMatcher isEqual(T input, T value) {
        return wrap(input, Matchers.is(value));
    }

}

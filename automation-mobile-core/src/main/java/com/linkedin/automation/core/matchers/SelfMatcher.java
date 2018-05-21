package com.linkedin.automation.core.matchers;

import com.linkedin.automation.core.logger.Logger;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.SelfDescribing;
import org.hamcrest.StringDescription;
import org.hamcrest.core.AllOf;

/**
 * Base class for matchers that already contain input object for evaluation that should be applied, so they don't need
 * any external object for that. For this reason, the class is not parameterized with the type of applied input object
 * (unlike <i>Hamcrest</i> {@link Matcher}s), and basic <code>check()</code> method don't have a parameter for input
 * object (unlike {@link Matcher#matches(Object)}). Furthermore, for simplicity, {@link #check(Description, boolean)}
 * method is a combination of similar {@link Matcher#matches(Object)} and
 * {@link Matcher#describeMismatch(Object, Description)} methods. This done also to avoid multiple matcher evaluations
 * if any (see {@link AllOf} matcher for example). It also allow to log all evaluations performed.
 */
public abstract class SelfMatcher implements SelfDescribing {
    /**
     * Perform check for this matcher, append description (if necessary)
     *
     * @param mismatchDescription the mismatch description
     * @param doAppend            if append the description
     * @return the result of evaluating this matcher
     */
    protected abstract boolean check(Description mismatchDescription, boolean doAppend);

    /**
     * Perform check, append description and log all information (if necessary)
     *
     * @param mismatchDescription the mismatch description
     * @param doAppend            if append the description and log
     * @return the result of evaluating this matcher
     */
    protected boolean logCheck(Description mismatchDescription, boolean doAppend) {
        Boolean check = check(mismatchDescription, doAppend);
        if (doAppend)
            Logger.debug(String.format("{%-5S} = %s", check, this));
        return check;
    }

    /**
     * Perform check, don't append description but log all information (if necessary)
     *
     * @param doAppend if append the log
     * @return the result of evaluating this matcher
     */
    public boolean check(boolean doAppend) {
        return logCheck(Description.NONE, doAppend);
    }

    /**
     * Perform check, don't append description but log everything
     *
     * @return the result of evaluating this matcher
     */
    public boolean check() {
        return logCheck(Description.NONE, true);
    }

    @Override
    public abstract void describeTo(Description description);

    @Override
    public String toString() {
        return StringDescription.toString(this);
    }
}

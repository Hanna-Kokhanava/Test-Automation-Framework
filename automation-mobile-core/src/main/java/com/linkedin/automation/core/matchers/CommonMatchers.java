package com.linkedin.automation.core.matchers;

import com.beust.jcommander.internal.Lists;
import org.hamcrest.Description;
import org.hamcrest.Factory;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class CommonMatchers {

    private static final class AllOf extends SelfMatcher {
        private final Iterable<SelfMatcher> matchers;

        private AllOf(Iterable<SelfMatcher> matchers) {
            this.matchers = matchers;
        }

        @Override
        protected boolean check(Description mismatchDescription, boolean doAppend) {
            List<Boolean> results = Lists.newArrayList();
            for (SelfMatcher matcher : matchers) {
                results.add(matcher.logCheck(mismatchDescription, doAppend));
            }
            return results.stream().allMatch(aBoolean -> Objects.equals(aBoolean, true));
        }

        @Override
        public void describeTo(Description description) {
            description.appendList("(", " and ", ")", matchers);
        }
    }

    private static final class AlwaysFalse extends SelfMatcher {
        private final String message;

        public AlwaysFalse() {
            this("FALSE");
        }

        public AlwaysFalse(String message) {
            this.message = message;
        }

        @Override
        protected boolean check(Description mismatchDescription, boolean doAppend) {
            return false;
        }

        @Override
        public void describeTo(Description description) {
            description.appendText(message);
        }
    }

    private static final class AlwaysTrue extends SelfMatcher {
        private final String message;

        public AlwaysTrue() {
            this("TRUE");
        }

        public AlwaysTrue(String message) {
            this.message = message;
        }

        @Override
        protected boolean check(Description mismatchDescription, boolean doAppend) {
            return true;
        }

        @Override
        public void describeTo(Description description) {
            description.appendText(message);
        }
    }

    private static final class AnyOf extends SelfMatcher {
        private final Iterable<SelfMatcher> matchers;

        private AnyOf(Iterable<SelfMatcher> matchers) {
            this.matchers = matchers;
        }

        @Override
        protected boolean check(Description mismatchDescription, boolean doAppend) {
            List<Boolean> results = Lists.newLinkedList();
            for (SelfMatcher matcher : matchers) {
                results.add(matcher.logCheck(mismatchDescription, doAppend));
            }
            return results.stream().anyMatch(aBoolean -> Objects.equals(aBoolean, true));
        }

        @Override
        public void describeTo(Description description) {
            description.appendList("(", " or ", ")", matchers);
        }
    }

    private static final class Not extends SelfMatcher {
        private final SelfMatcher matcher;

        private Not(SelfMatcher matcher) {
            this.matcher = matcher;
        }

        @Override
        protected boolean check(Description mismatchDescription, boolean doAppend) {
            boolean notMatched = !matcher.logCheck(mismatchDescription, false);
            if (!notMatched && doAppend)
                mismatchDescription
                        .appendText("was ")
                        .appendDescriptionOf(matcher)
                        .appendText(", ");
            return notMatched;
        }

        @Override
        public void describeTo(Description description) {
            description.appendText("not [")
                    .appendDescriptionOf(matcher)
                    .appendText("]");
        }
    }

    public static final class CombinableBothMatcher {
        private final SelfMatcher first;

        public CombinableBothMatcher(SelfMatcher matcher) {
            this.first = matcher;
        }

        public SelfMatcher and(SelfMatcher other) {
            return allOf(first, other);
        }
    }

    public static final class CombinableEitherMatcher {
        private final SelfMatcher first;

        public CombinableEitherMatcher(SelfMatcher matcher) {
            this.first = matcher;
        }

        public SelfMatcher or(SelfMatcher other) {
            return anyOf(first, other);
        }
    }

    /**
     * Creates a matcher that evaluates to {@code true} if each of its components evaluates to {@code true}.
     */
    public static SelfMatcher allOf(Iterable<SelfMatcher> matchers) {
        return new AllOf(matchers);
    }

    /**
     * Creates a matcher that evaluates to {@code true} if each of its components evaluates to {@code true}.
     */
    public static SelfMatcher allOf(SelfMatcher... matchers) {
        return new AllOf(Arrays.asList(matchers));
    }

    /**
     * Creates a matcher that always evaluates to {@code false}
     */
    public static SelfMatcher alwaysFalse() {
        return new AlwaysFalse();
    }

    /**
     * Creates a matcher that always evaluates to {@code false}, but describes itself with the specified {@link String}.
     *
     * @param description a meaningful {@link String} used when describing itself
     */
    public static SelfMatcher alwaysFalse(String description) {
        return new AlwaysFalse(description);
    }

    /**
     * Creates a matcher that always evaluates to {@code true}
     */
    public static SelfMatcher alwaysTrue() {
        return new AlwaysTrue();
    }

    /**
     * Creates a matcher that always evaluates to {@code true}, but describes itself with the specified {@link String}.
     *
     * @param description a meaningful {@link String} used when describing itself
     */
    public static SelfMatcher alwaysTrue(String description) {
        return new AlwaysTrue(description);
    }

    /**
     * Creates a matcher that evaluates to {@code true} if any one of its components evaluates to {@code true}.
     */
    public static SelfMatcher anyOf(Iterable<SelfMatcher> matchers) {
        return new AnyOf(matchers);
    }

    /**
     * Creates a matcher that evaluates to {@code true} if any one of its components evaluates to {@code true}.
     */
    public static SelfMatcher anyOf(SelfMatcher... matchers) {
        return new AnyOf(Arrays.asList(matchers));
    }

    /**
     * Creates a matcher that evaluates to {@code true} if the given matcher evaluates to {@code false}.
     */
    public static SelfMatcher not(SelfMatcher matcher) {
        return new Not(matcher);
    }

    /**
     * Creates a matcher that matches when both of the specified matchers match the examined objects.
     */
    @Factory
    public static CombinableBothMatcher both(SelfMatcher matcher) {
        return new CombinableBothMatcher(matcher);
    }

    /**
     * Creates a matcher that matches when either of the specified matchers match the examined objects.
     */
    @Factory
    public static CombinableEitherMatcher either(SelfMatcher matcher) {
        return new CombinableEitherMatcher(matcher);
    }
}

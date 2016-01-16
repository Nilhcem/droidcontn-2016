package com.nilhcem.droidcontn.utils;

/**
 * Preconditions inspired by
 * https://github.com/google/guava/blob/master/guava/src/com/google/common/base/Preconditions.java
 */
public final class Preconditions {

    private Preconditions() {
        throw new UnsupportedOperationException();
    }

    /**
     * Ensures the truth of an expression involving one or more parameters to the calling method.
     *
     * @param expression a boolean expression
     * @throws IllegalArgumentException if {@code expression} is false
     */
    public static void checkArgument(boolean expression) {
        if (!expression) {
            throw new IllegalArgumentException();
        }
    }
}

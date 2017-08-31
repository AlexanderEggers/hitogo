package com.mordag.crouton;

final class StringUtils {

    private StringUtils() {
        throw new IllegalStateException("Methods can only access by static methods.");
    }

    static boolean isNotEmpty(String text) {
        return !text.isEmpty();
    }

    static boolean isEmpty(String text) {
        return text.isEmpty();
    }
}

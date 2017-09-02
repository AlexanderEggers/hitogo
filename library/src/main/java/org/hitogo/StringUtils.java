package org.hitogo;

import android.support.annotation.NonNull;

@SuppressWarnings({"WeakerAccess", "unused"})
final class StringUtils {

    private StringUtils() {
        throw new IllegalStateException("Methods can only be accessed by static methods.");
    }

    static boolean isNotEmpty(@NonNull String text) {
        return !text.isEmpty();
    }

    static boolean isEmpty(@NonNull String text) {
        return text.isEmpty();
    }
}

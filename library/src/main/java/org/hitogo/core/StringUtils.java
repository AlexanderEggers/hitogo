package org.hitogo.core;

import android.support.annotation.NonNull;

@SuppressWarnings({"WeakerAccess", "unused"})
public final class StringUtils {

    private StringUtils() {
        throw new IllegalStateException("Methods can only be accessed by static methods.");
    }

    public static boolean isNotEmpty(@NonNull String text) {
        return !text.isEmpty();
    }

    public static boolean isEmpty(@NonNull String text) {
        return text.isEmpty();
    }
}

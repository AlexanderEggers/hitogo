package org.hitogo.button.core;

import android.support.annotation.Nullable;

@SuppressWarnings({"WeakerAccess", "unused"})
public interface ButtonListener<T> {
    void onClick(@Nullable T parameter);
}

package org.hitogo.button.core;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.hitogo.alert.core.Alert;

@SuppressWarnings({"WeakerAccess", "unused"})
public interface ButtonListener<T> {
    void onClick(@NonNull Alert alert, @Nullable T parameter);
}

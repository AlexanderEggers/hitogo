package org.hitogo.button.core;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.hitogo.alert.core.Alert;
import org.hitogo.button.core.ButtonListener;

@SuppressWarnings({"WeakerAccess", "unused"})
public class DefaultButtonListener implements ButtonListener {

    @Override
    public void onClick(@NonNull Alert alert, @Nullable Object parameter) {
        //default implementation of hitogo button listener does nothing
    }
}

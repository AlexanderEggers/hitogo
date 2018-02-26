package org.hitogo.button.core;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.hitogo.alert.core.Alert;

/**
 * This class is the default implementation for the ButtonListener class and does nothing.
 *
 * @see ButtonListener
 * @since 1.0.0
 */
public class DefaultButtonListener implements ButtonListener {

    @Override
    public void onClick(@NonNull Alert alert, @Nullable Object parameter) {
        //do nothing
    }
}

package org.hitogo.button.core;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.hitogo.alert.core.Alert;

/**
 * This class is used to implement a callback to a certain button object. This callback/listener
 * will be executed if the user clicks the related button element/view.
 *
 * @param <T> type which is extending the optional button parameter object
 * @since 1.0.0
 */
public interface ButtonListener<T> {

    /**
     * This method will be called if the user has clicked it's button element. The method is only
     * once executed and needs to run on the main thread.
     *
     * @param alert     related alert which triggered this button listener
     * @param parameter optional button parameter which has been attached to this button element
     *                  when specifying the setButtonListener (ButtonBuilder)
     * @since 1.0.0
     */
    @MainThread
    void onClick(@NonNull Alert alert, @Nullable T parameter);
}

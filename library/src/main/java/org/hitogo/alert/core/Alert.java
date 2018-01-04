package org.hitogo.alert.core;

import android.content.Context;

/**
 * Public api interface for the basic alert object. This interface includes the most basic alert
 * methods which is provided by the builder classes.
 *
 * @param <T> Type of the AlertParams object that has been used for the related alert object.
 * @see AlertParams
 */
public interface Alert<T extends AlertParams> {

    /**
     * Displays this alert object at the user's screen if the alert is not visible yet.
     */
    void show();

    void close();

    boolean isDetached();

    boolean isAttached();

    boolean isClosing();

    int hashCode();

    int getState();

    boolean equals(final Object obj);

    AlertType getType();

    T getParams();

    String getTag();

    Context getContext();
}

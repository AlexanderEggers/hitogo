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
     * Displays this alert object on the user screen if the alert is not visible yet.
     */
    void show();

    /**
     * Closes this alert object on the user screen if it's still visible.
     */
    void close();

    /**
     * Determines if the alert is attached (visible) at the user screen.
     *
     * @return True if the alert is attached, otherwise false.
     */
    boolean isAttached();

    /**
     * Determines if the alert is detached (invisible) from the user screen.
     *
     * @return True if the alert is detached, otherwise false.
     */
    boolean isDetached();

    /**
     * Determines the moment when the alert is being detached from the user screen. That means that
     * isAttached equals false and isDetached is still false. This caused by the animation that is
     * execute between showing and hiding.
     *
     * @return True if the alert is in the process of being closed, otherwise false.
     */
    boolean isClosing();

    /**
     * Returns the alert hashcode.
     *
     * @return Hashcode for this alert.
     */
    int hashCode();

    /**
     * Returns the alert custom state.
     *
     * @return State for this alert.
     */
    int getState();

    boolean equals(final Object obj);

    /**
     * Returns the alert type. The types are based on the enum AlertType which includes VIEW,
     * DIALOG and POPUP.
     *
     * @return Type for this alert.
     */
    AlertType getType();

    /**
     * Returns the alert params object. The params object is storing all values for the alert.
     *
     * @return Params object for this alert.
     */
    T getParams();

    /**
     * Returns the alert tag.
     *
     * @return Tag for this alert.
     */
    String getTag();

    /**
     * Returns the alert context.
     *
     * @return Context for this alert.
     */
    Context getContext();
}

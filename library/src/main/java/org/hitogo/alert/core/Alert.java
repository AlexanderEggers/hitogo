package org.hitogo.alert.core;

import android.content.Context;

/**
 * Public api interface for the basic alert object. This interface includes the most basic alert
 * methods which is provided by the builder classes.
 *
 * @param <T> Type of the AlertParams object that the alert is using.
 * @see AlertParams
 * @see AlertImpl
 * @since 1.0.0
 */
public interface Alert<T extends AlertParams> {

    /**
     * Displays this alert object on the user screen if the alert is not visible yet.
     *
     * @since 1.0.0
     */
    void show();

    /**
     * Closes this alert object on the user screen if it's still visible.
     *
     * @since 1.0.0
     */
    void close();

    /**
     * Determines if the alert is attached (visible) at the user screen.
     *
     * @return True if the alert is attached, otherwise false.
     * @since 1.0.0
     */
    boolean isAttached();

    /**
     * Determines if the alert is detached (invisible) from the user screen.
     *
     * @return True if the alert is detached, otherwise false.
     * @since 1.0.0
     */
    boolean isDetached();

    /**
     * Determines the moment when the alert is being detached from the user screen. That means that
     * isAttached equals false and isDetached is still false. This caused by the animation that is
     * execute between showing and hiding.
     *
     * @return True if the alert is in the process of being closed, otherwise false.
     * @since 1.0.0
     */
    boolean isClosing();

    /**
     * Returns the alert hashcode.
     *
     * @return an Int
     * @since 1.0.0
     */
    int hashCode();

    /**
     * Returns the alert custom state.
     *
     * @return a positive Int or -1 if non is set
     * @since 1.0.0
     */
    int getState();

    /**
     * Compares this alert to the given alert.
     *
     * @return True if the two compared alerts are equals, false otherwise
     * @since 1.0.0
     */
    boolean equals(final Object obj);

    /**
     * Returns the alert type. The types are based on the enum AlertType which includes VIEW,
     * DIALOG, POPUP and OTHER.
     *
     * @return Type for this alert
     * @see AlertType
     * @since 1.0.0
     */
    AlertType getAlerType();

    /**
     * Returns the alert params object. The params object is storing all values for the alert.
     *
     * @return an object which is extending AlertParams
     * @since 1.0.0
     */
    T getParams();

    /**
     * Determines if the alert has a priority or not.
     *
     * @return True if the alert has a priority, false otherwise.
     * @since 1.0.0
     */
    boolean hasPriority();

    /**
     * Determines if the alert has been execute by a HitogoContainer that is an instance of
     * Activity.
     *
     * @return a boolean
     * @since 1.0.0
     */
    boolean isExecutedByActivity();

    /**
     * Return the animation duration length for the show-/hide-process.
     *
     * @return a long
     * @since 1.0.0
     */
    long getAnimationDuration();

    /**
     * Returns the current priority.
     *
     * @return an Integer object or null
     * @since 1.0.0
     */
    Integer getPriority();

    /**
     * Returns the alert tag.
     *
     * @return a String or null
     * @since 1.0.0
     */
    String getTag();

    /**
     * Returns the alert context.
     *
     * @return a Context
     * @since 1.0.0
     */
    Context getContext();
}

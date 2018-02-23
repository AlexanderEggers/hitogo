package org.hitogo.alert.view;

import android.view.View;

import org.hitogo.alert.core.Alert;
import org.hitogo.alert.core.AlertImpl;
import org.hitogo.alert.core.AlertParams;
import org.hitogo.alert.core.AlertType;

/**
 * Public api interface for the basic view alert object. This interface includes the most basic
 * view alert methods which is provided by the builder classes.
 *
 * @see AlertParams
 * @see AlertImpl
 * @since 1.0.0
 */
public interface ViewAlert extends Alert<ViewAlertParams> {

    /**
     * Displays this alert object on the user screen if the alert is not visible yet.
     *
     * @param force Determines if the animation for the show-process should displayed or not.
     * @since 1.0.0
     */
    void show(final boolean force);

    /**
     * Prepares the show-process for this alert. The alert will stay invisible for later.
     *
     * @since 1.0.0
     */
    void showLater();

    /**
     * Prepares the show-process for this alert. Depending on the input, the alert will be made
     * visible or stay invisible for later.
     *
     * @param showLater Determines if the alert should be displayed later.
     * @since 1.0.0
     */
    void showLater(final boolean showLater);

    /**
     * Delays the show-process for this alert. The delay is depending the input.
     *
     * @param millis Delay in milliseconds.
     * @since 1.0.0
     */
    void showDelayed(final long millis);

    /**
     * Delays the show-process for this alert. The delay is depending the input.
     *
     * @param millis Delay in milliseconds.
     * @param force  Determines if the animation for the show-process should displayed or not.
     * @since 1.0.0
     */
    void showDelayed(final long millis, final boolean force);

    /**
     * Closes this alert object on the user screen if it's still visible.
     *
     * @param force Determines if the animation for the hide-process should displayed or not.
     * @since 1.0.0
     */
    void close(final boolean force);

    /**
     * Returns the view of the alert. This value is usually non-null if the alert is attached
     * to the view hierarchy (activity or fragment).
     *
     * @return View of the alert. Null if the alert is not using the view object.
     * @since 1.0.0
     */
    View getView();

    /**
     * Returns if the alert can execute animations. This is determined by the HitogoAnimation object
     * if it is attached to the alert.
     *
     * @return True if the alert can execute animations, false otherwise.
     * @see org.hitogo.core.HitogoAnimation
     * @since 1.0.0
     */
    boolean hasAnimation();

    /**
     * Returns if the alert is closing others (of the same alert type) if it's made visible.
     *
     * @return True if the alert is closing others, otherwise false.
     * @see AlertType
     * @since 1.0.0
     */
    boolean isClosingOthers();
}

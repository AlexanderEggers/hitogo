package org.hitogo.alert.popup;

import androidx.annotation.Nullable;
import android.widget.PopupWindow;

import org.hitogo.alert.core.Alert;
import org.hitogo.alert.core.AlertImpl;
import org.hitogo.alert.core.AlertParams;

/**
 * Public api interface for the basic popup alert object. This interface includes the most basic
 * popup alert methods which is provided by the builder classes.
 *
 * @see AlertParams
 * @see AlertImpl
 * @since 1.0.0
 */
public interface PopupAlert extends Alert<PopupAlertParams> {

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
     * Returns the popup of the alert.
     *
     * @return Popup of the alert. Null if the alert is not using the popup object.
     * @since 1.0.0
     */
    @Nullable
    PopupWindow getPopup();
}

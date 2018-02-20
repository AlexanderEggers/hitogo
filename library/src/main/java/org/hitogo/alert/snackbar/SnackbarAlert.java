package org.hitogo.alert.snackbar;

import org.hitogo.alert.core.Alert;
import org.hitogo.alert.core.AlertImpl;
import org.hitogo.alert.core.AlertParams;

/**
 * Public api interface for the basic snackbar alert object. This interface includes the most basic
 * snackbar alert methods which is provided by the builder classes.
 *
 * @see AlertParams
 * @see AlertImpl
 * @since 1.0.0
 */
public interface SnackbarAlert extends Alert<SnackbarAlertParams> {

    /**
     * Returns the underlying object of the alert.
     *
     * @return Base object of the alert. Null if the alert is not from type OTHER (AlertType).
     * @since 1.0.0
     */
    Object getOther();
}

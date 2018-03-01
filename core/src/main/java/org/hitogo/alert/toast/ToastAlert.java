package org.hitogo.alert.toast;

import org.hitogo.alert.core.Alert;
import org.hitogo.alert.core.AlertImpl;
import org.hitogo.alert.core.AlertParams;

/**
 * Public api interface for the basic toast alert object. This interface includes the most basic
 * toast alert methods which is provided by the builder classes.
 *
 * @see AlertParams
 * @see AlertImpl
 * @since 1.0.0
 */
public interface ToastAlert extends Alert<ToastAlertParams> {

    /**
     * Returns the underlying object of the alert.
     *
     * @return Base object of the alert. Null if the alert is not from type OTHER (AlertType).
     * @since 1.0.0
     */
    <O extends Object> O getOther();
}

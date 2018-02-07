package org.hitogo.alert.toast;

import org.hitogo.alert.core.Alert;

public interface ToastAlert extends Alert<ToastAlertParams> {

    /**
     * Returns the underlying object of the alert.
     *
     * @return Base object of the alert. Null if the alert is not from type OTHER (AlertType).
     * @since 1.0.0
     */
    Object getOther();
}

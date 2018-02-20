package org.hitogo.alert.popup;

import android.support.annotation.NonNull;

import org.hitogo.alert.core.AlertBuilder;
import org.hitogo.alert.core.AlertImpl;
import org.hitogo.alert.core.AlertParams;

/**
 * Factory interface which can be used to structure the ability to generate PopupAlerts.
 *
 * @param <P> AlertBuilder interface which is responsible to create the AlertDialog.
 * @since 1.0.0
 */
public interface PopupAlertFactory<P extends AlertBuilder> {

    /**
     * Creates a new PopupAlertBuilder using the default HitogoController values. The relevant
     * values are provided by the methods provideDefaultPopupClass and
     * provideDefaultPopupParamsClass.
     *
     * @return a DialogAlertBuilder
     * @see org.hitogo.core.HitogoController
     * @since 1.0.0
     */
    @NonNull
    P asPopupAlert();

    /**
     * Creates a new PopupAlertBuilder using the given target class and the default params class
     * definition provided by the HitogoController. The relevant value is provided by the method
     * provideDefaultPopupParamsClass.
     *
     * @return a PopupAlertBuilder
     * @see org.hitogo.core.HitogoController
     * @since 1.0.0
     */
    @NonNull
    P asPopupAlert(@NonNull Class<? extends AlertImpl> targetClass);

    /**
     * Creates a new PopupAlertBuilder using the given target class and params class.
     *
     * @return a PopupAlertBuilder
     * @since 1.0.0
     */
    @NonNull
    P asPopupAlert(@NonNull Class<? extends AlertImpl> targetClass,
                   @NonNull Class<? extends AlertParams> paramClass);
}

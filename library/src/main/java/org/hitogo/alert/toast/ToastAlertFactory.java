package org.hitogo.alert.toast;

import android.support.annotation.NonNull;

import org.hitogo.alert.core.AlertBuilderBase;
import org.hitogo.alert.core.AlertImpl;
import org.hitogo.alert.core.AlertParams;

/**
 * Factory interface which can be used to structure the ability to generate ToastAlerts.
 *
 * @param <T> AlertBuilder interface which is responsible to create the ToastAlert.
 * @since 1.0.0
 */
public interface ToastAlertFactory<T extends AlertBuilderBase> {

    /**
     * Creates a new ToastAlertBuilder using the default HitogoController values. The relevant
     * values are provided by the methods provideDefaultToastClass and
     * provideDefaultToastParamsClass.
     *
     * @return a ToastAlertBuilder
     * @see org.hitogo.core.HitogoController
     * @since 1.0.0
     */
    T asToastAlert();

    /**
     * Creates a new ToastAlertBuilder using the given target class and the default params class
     * definition provided by the HitogoController. The relevant value is provided by the method
     * provideDefaultToastParamsClass.
     *
     * @return a ToastAlertBuilder
     * @see org.hitogo.core.HitogoController
     * @since 1.0.0
     */
    T asToastAlert(@NonNull Class<? extends AlertImpl> targetClass);

    /**
     * Creates a new ToastAlertBuilder using the given target class and params class.
     *
     * @return a ToastAlertBuilder
     * @since 1.0.0
     */
    T asToastAlert(@NonNull Class<? extends AlertImpl> targetClass,
                   @NonNull Class<? extends AlertParams> paramClass);
}

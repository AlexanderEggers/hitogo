package org.hitogo.alert.snackbar;

import android.support.annotation.NonNull;

import org.hitogo.alert.core.AlertBuilderBase;
import org.hitogo.alert.core.AlertImpl;
import org.hitogo.alert.core.AlertParams;

/**
 * Factory interface which can be used to structure the ability to generate SnackbarAlerts.
 *
 * @param <S> AlertBuilder interface which is responsible to create the SnackbarAlert.
 * @since 1.0.0
 */
public interface SnackbarAlertFactory<S extends AlertBuilderBase> {

    /**
     * Creates a new SnackbarAlertBuilder using the default HitogoController values. The relevant
     * values are provided by the methods provideDefaultSnackbarClass and
     * provideDefaultSnackbarParamsClass.
     *
     * @return a SnackbarAlertBuilder
     * @see org.hitogo.core.HitogoController
     * @since 1.0.0
     */
    S asSnackbarAlert();

    /**
     * Creates a new SnackbarAlertBuilder using the given target class and the default params class
     * definition provided by the HitogoController. The relevant value is provided by the method
     * provideDefaultSnackbarParamsClass.
     *
     * @return a SnackbarAlertBuilder
     * @see org.hitogo.core.HitogoController
     * @since 1.0.0
     */
    S asSnackbarAlert(@NonNull Class<? extends AlertImpl> targetClass);

    /**
     * Creates a new SnackbarAlertBuilder using the given target class and params class.
     *
     * @return a SnackbarAlertBuilder
     * @since 1.0.0
     */
    S asSnackbarAlert(@NonNull Class<? extends AlertImpl> targetClass,
                      @NonNull Class<? extends AlertParams> paramClass);
}

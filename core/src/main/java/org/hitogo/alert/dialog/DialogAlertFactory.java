package org.hitogo.alert.dialog;

import android.support.annotation.NonNull;

import org.hitogo.alert.core.AlertBuilder;
import org.hitogo.alert.core.AlertImpl;
import org.hitogo.alert.core.AlertParams;

/**
 * Factory interface which can be used to structure the ability to generate DialogAlerts.
 *
 * @param <D> AlertBuilder interface which is responsible to create the AlertDialog.
 * @since 1.0.0
 */
public interface DialogAlertFactory<D extends AlertBuilder> {

    /**
     * Creates a new DialogAlertBuilder using the default HitogoController values. The relevant
     * values are provided by the methods provideDefaultDialogClass and
     * provideDefaultDialogParamsClass.
     *
     * @return a DialogAlertBuilder
     * @see org.hitogo.core.HitogoController
     * @since 1.0.0
     */
    @NonNull
    D asDialogAlert();

    /**
     * Creates a new DialogAlertBuilder using the given target class and the default params class
     * definition provided by the HitogoController. The relevant value is provided by the method
     * provideDefaultDialogParamsClass.
     *
     * @return a DialogAlertBuilder
     * @see org.hitogo.core.HitogoController
     * @since 1.0.0
     */
    @NonNull
    D asDialogAlert(@NonNull Class<? extends AlertImpl> targetClass);

    /**
     * Creates a new DialogAlertBuilder using the given target class and params class.
     *
     * @return a DialogAlertBuilder
     * @since 1.0.0
     */
    @NonNull
    D asDialogAlert(@NonNull Class<? extends AlertImpl> targetClass,
                    @NonNull Class<? extends AlertParams> paramClass);
}

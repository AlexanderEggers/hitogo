package org.hitogo.alert.view;

import androidx.annotation.NonNull;

import org.hitogo.alert.core.AlertBuilder;
import org.hitogo.alert.core.AlertImpl;
import org.hitogo.alert.core.AlertParams;

/**
 * Factory interface which can be used to structure the ability to generate ViewAlerts.
 *
 * @param <V> AlertBuilder interface which is responsible to create the ViewAlert.
 * @since 1.0.0
 */
public interface ViewAlertFactory<V extends AlertBuilder> {

    /**
     * Creates a new ViewAlertBuilder using the default HitogoController values. The relevant
     * values are provided by the methods provideDefaultViewClass and
     * provideDefaultViewParamsClass.
     *
     * @return a ViewAlertBuilder
     * @see org.hitogo.core.HitogoController
     * @since 1.0.0
     */
    @NonNull
    V asViewAlert();

    /**
     * Creates a new ViewAlertBuilder using the given target class and the default params class
     * definition provided by the HitogoController. The relevant value is provided by the method
     * provideDefaultViewParamsClass.
     *
     * @return a ViewAlertBuilder
     * @see org.hitogo.core.HitogoController
     * @since 1.0.0
     */
    @NonNull
    V asViewAlert(@NonNull Class<? extends AlertImpl> targetClass);

    /**
     * Creates a new ViewAlertBuilder using the given target class and params class.
     *
     * @return a ViewAlertBuilder
     * @since 1.0.0
     */
    @NonNull
    V asViewAlert(@NonNull Class<? extends AlertImpl> targetClass,
                  @NonNull Class<? extends AlertParams> paramClass);
}

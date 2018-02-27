package org.hitogo.button.view;

import android.support.annotation.NonNull;

import org.hitogo.button.core.ButtonBuilder;
import org.hitogo.button.core.ButtonImpl;
import org.hitogo.button.core.ButtonParams;

/**
 * Factory interface which can be used to structure the ability to generate ViewButtons.
 *
 * @param <B> ButtonBuilder interface which is responsible to create the ViewButton.
 * @since 1.0.0
 */
public interface ViewButtonFactory<B extends ButtonBuilder> {

    /**
     * Creates a new ViewButtonBuilder using the default HitogoController values. The relevant
     * values are provided by the methods provideDefaultViewButtonClass and
     * provideDefaultViewButtonParamsClass.
     *
     * @return a ViewButtonBuilder
     * @see org.hitogo.core.HitogoController
     * @since 1.0.0
     */
    @NonNull
    B asViewButton();

    /**
     * Creates a new ViewButtonBuilder using the given target class and the default params class
     * definition provided by the HitogoController. The relevant value is provided by the method
     * provideDefaultViewButtonParamsClass.
     *
     * @return a ViewButtonBuilder
     * @see org.hitogo.core.HitogoController
     * @since 1.0.0
     */
    @NonNull
    B asViewButton(@NonNull Class<? extends ButtonImpl> targetClass);

    /**
     * Creates a new ViewButtonBuilder using the given target class and params class.
     *
     * @return a ViewButtonBuilder
     * @since 1.0.0
     */
    @NonNull
    B asViewButton(@NonNull Class<? extends ButtonImpl> targetClass,
                   @NonNull Class<? extends ButtonParams> paramClass);
}

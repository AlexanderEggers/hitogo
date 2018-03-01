package org.hitogo.button.close;

import android.support.annotation.NonNull;

import org.hitogo.button.core.ButtonBuilder;
import org.hitogo.button.core.ButtonImpl;
import org.hitogo.button.core.ButtonParams;

/**
 * Factory interface which can be used to structure the ability to generate CloseButtons.
 *
 * @param <B> ButtonBuilder interface which is responsible to create the CloseButton.
 * @since 1.0.0
 */
public interface CloseButtonFactory<B extends ButtonBuilder> {

    /**
     * Creates a new ViewButtonBuilder using the default HitogoController values. The relevant
     * values are provided by the methods provideDefaultCloseButtonClass and
     * provideDefaultCloseButtonParamsClass.
     *
     * @return a ViewButtonBuilder
     * @see org.hitogo.core.HitogoController
     * @since 1.0.0
     */
    @NonNull
    B asCloseButton();

    /**
     * Creates a new ViewButtonBuilder using the given target class and the default params class
     * definition provided by the HitogoController. The relevant value is provided by the method
     * provideDefaultCloseButtonParamsClass.
     *
     * @return a ViewButtonBuilder
     * @see org.hitogo.core.HitogoController
     * @since 1.0.0
     */
    @NonNull
    B asCloseButton(@NonNull Class<? extends ButtonImpl> targetClass);

    /**
     * Creates a new ViewButtonBuilder using the given target class and params class.
     *
     * @return a ViewButtonBuilder
     * @since 1.0.0
     */
    @NonNull
    B asCloseButton(@NonNull Class<? extends ButtonImpl> targetClass,
                    @NonNull Class<? extends ButtonParams> paramClass);
}

package org.hitogo.button.text;

import android.support.annotation.NonNull;

import org.hitogo.button.core.ButtonBuilder;
import org.hitogo.button.core.ButtonImpl;
import org.hitogo.button.core.ButtonParams;

/**
 * Factory interface which can be used to structure the ability to generate TextButtons.
 *
 * @param <B> ButtonBuilder interface which is responsible to create the TextButton.
 * @since 1.0.0
 */
public interface TextButtonFactory<B extends ButtonBuilder> {

    /**
     * Creates a new TextButtonBuilder using the default HitogoController values. The relevant
     * values are provided by the methods provideDefaultTextButtonClass and
     * provideDefaultTextButtonParamsClass.
     *
     * @return a TextButtonBuilder
     * @see org.hitogo.core.HitogoController
     * @since 1.0.0
     */
    @NonNull
    B asTextButton();

    /**
     * Creates a new TextButtonBuilder using the given target class and the default params class
     * definition provided by the HitogoController. The relevant value is provided by the method
     * provideDefaultTextButtonParamsClass.
     *
     * @return a TextButtonBuilder
     * @see org.hitogo.core.HitogoController
     * @since 1.0.0
     */
    @NonNull
    B asTextButton(@NonNull Class<? extends ButtonImpl> targetClass);

    /**
     * Creates a new TextButtonBuilder using the given target class and params class.
     *
     * @return a TextButtonBuilder
     * @since 1.0.0
     */
    @NonNull
    B asTextButton(@NonNull Class<? extends ButtonImpl> targetClass,
                   @NonNull Class<? extends ButtonParams> paramClass);
}

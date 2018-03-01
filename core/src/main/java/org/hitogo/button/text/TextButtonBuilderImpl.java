package org.hitogo.button.text;

import android.support.annotation.NonNull;

import org.hitogo.button.core.ButtonBuilderImpl;
import org.hitogo.button.core.ButtonImpl;
import org.hitogo.button.core.ButtonParams;
import org.hitogo.button.core.ButtonType;
import org.hitogo.core.HitogoContainer;
import org.hitogo.core.HitogoController;

/**
 * Builder which includes all basic method to assign specific values to the text button.
 *
 * @see org.hitogo.button.core.Button
 * @since 1.0.0
 */
public class TextButtonBuilderImpl extends ButtonBuilderImpl<TextButtonBuilder, TextButton> implements TextButtonBuilder {

    /**
     * Default constructor for the TextButtonBuilderImpl.
     *
     * @param targetClass Class object for the requested button.
     * @param paramClass  Class object for the params object which is used by the button.
     * @param container   Container which is used as a reference for this button (context, view,
     *                    controller).
     * @see HitogoContainer
     * @see HitogoController
     * @see ButtonType
     * @since 1.0.0
     */
    public TextButtonBuilderImpl(@NonNull Class<? extends ButtonImpl> targetClass,
                                 @NonNull Class<? extends ButtonParams> paramClass,
                                 @NonNull HitogoContainer container) {
        super(targetClass, paramClass, container, ButtonType.TEXT);
    }
}

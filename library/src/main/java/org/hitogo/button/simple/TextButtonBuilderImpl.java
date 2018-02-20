package org.hitogo.button.simple;

import android.support.annotation.NonNull;

import org.hitogo.button.core.ButtonBuilderImpl;
import org.hitogo.button.core.ButtonImpl;
import org.hitogo.button.core.ButtonParams;
import org.hitogo.button.core.ButtonType;
import org.hitogo.core.HitogoContainer;

public class TextButtonBuilderImpl extends ButtonBuilderImpl<TextButtonBuilder, TextButton> implements TextButtonBuilder {

    public TextButtonBuilderImpl(@NonNull Class<? extends ButtonImpl> targetClass,
                                 @NonNull Class<? extends ButtonParams> paramClass,
                                 @NonNull HitogoContainer container) {
        super(targetClass, paramClass, container, ButtonType.TEXT);
    }
}

package org.hitogo.button.simple;

import android.support.annotation.NonNull;

import org.hitogo.button.core.ButtonBuilder;
import org.hitogo.button.core.ButtonParams;

public interface SimpleButtonFactory<B extends ButtonBuilder> {

    B asSimpleButton();
    B asSimpleButton(@NonNull Class<? extends SimpleButtonImpl> targetClass);
    B asSimpleButton(@NonNull Class<? extends SimpleButtonImpl> targetClass,
                     @NonNull Class<? extends ButtonParams> paramClass);
}

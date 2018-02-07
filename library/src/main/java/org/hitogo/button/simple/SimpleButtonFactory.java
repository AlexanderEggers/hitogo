package org.hitogo.button.simple;

import android.support.annotation.NonNull;

import org.hitogo.button.core.ButtonBuilder;
import org.hitogo.button.core.ButtonImpl;
import org.hitogo.button.core.ButtonParams;

public interface SimpleButtonFactory<B extends ButtonBuilder> {

    B asSimpleButton();
    B asSimpleButton(@NonNull Class<? extends ButtonImpl> targetClass);
    B asSimpleButton(@NonNull Class<? extends ButtonImpl> targetClass,
                     @NonNull Class<? extends ButtonParams> paramClass);
}

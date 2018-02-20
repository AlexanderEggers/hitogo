package org.hitogo.button.simple;

import android.support.annotation.NonNull;

import org.hitogo.button.core.ButtonBuilder;
import org.hitogo.button.core.ButtonImpl;
import org.hitogo.button.core.ButtonParams;

public interface TextButtonFactory<B extends ButtonBuilder> {

    B asTextButton();
    B asTextButton(@NonNull Class<? extends ButtonImpl> targetClass);
    B asTextButton(@NonNull Class<? extends ButtonImpl> targetClass,
                   @NonNull Class<? extends ButtonParams> paramClass);
}

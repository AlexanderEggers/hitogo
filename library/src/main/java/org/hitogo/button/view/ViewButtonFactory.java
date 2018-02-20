package org.hitogo.button.view;

import android.support.annotation.NonNull;

import org.hitogo.button.core.ButtonBuilder;
import org.hitogo.button.core.ButtonImpl;
import org.hitogo.button.core.ButtonParams;

public interface ViewButtonFactory<B extends ButtonBuilder> {

    B asViewButton();
    B asViewButton(@NonNull Class<? extends ButtonImpl> targetClass);
    B asViewButton(@NonNull Class<? extends ButtonImpl> targetClass,
                   @NonNull Class<? extends ButtonParams> paramClass);
}

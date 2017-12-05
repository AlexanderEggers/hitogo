package org.hitogo.button.action;

import android.support.annotation.NonNull;

import org.hitogo.button.core.ButtonBuilder;
import org.hitogo.button.core.ButtonImpl;
import org.hitogo.button.core.ButtonParams;

public interface ActionButtonFactory<B extends ButtonBuilder> {

    B asButton();
    B asButton(@NonNull Class<? extends ButtonImpl> targetClass);
    B asButton(@NonNull Class<? extends ButtonImpl> targetClass,
               @NonNull Class<? extends ButtonParams> paramClass);
}

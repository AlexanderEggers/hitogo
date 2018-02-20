package org.hitogo.button.close;

import android.support.annotation.NonNull;

import org.hitogo.button.core.ButtonBuilder;
import org.hitogo.button.core.ButtonImpl;
import org.hitogo.button.core.ButtonParams;

public interface CloseButtonFactory<B extends ButtonBuilder> {

    B asCloseButton();

    B asCloseButton(@NonNull Class<? extends ButtonImpl> targetClass);

    B asCloseButton(@NonNull Class<? extends ButtonImpl> targetClass,
                    @NonNull Class<? extends ButtonParams> paramClass);
}

package org.hitogo.button.action;

import android.support.annotation.NonNull;

import org.hitogo.button.core.HitogoButtonBuilder;
import org.hitogo.button.core.HitogoButton;
import org.hitogo.button.core.HitogoButtonParams;

public interface HitogoActionFactory<B extends HitogoButtonBuilder> {

    B asButton();
    B asButton(@NonNull Class<? extends HitogoButton> targetClass);
    B asButton(@NonNull Class<? extends HitogoButton> targetClass,
               @NonNull Class<? extends HitogoButtonParams> paramClass);
}

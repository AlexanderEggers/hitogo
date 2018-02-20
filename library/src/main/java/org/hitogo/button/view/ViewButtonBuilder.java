package org.hitogo.button.view;

import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.hitogo.button.core.ButtonBuilder;

public interface ViewButtonBuilder extends ButtonBuilder<ViewButtonBuilder, ViewButton> {

    @NonNull
    ViewButtonBuilder setView(@IdRes int iconId);

    @NonNull
    ViewButtonBuilder setView(@IdRes int iconId, @IdRes @Nullable Integer clickId);
}

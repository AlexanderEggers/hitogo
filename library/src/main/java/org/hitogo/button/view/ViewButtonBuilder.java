package org.hitogo.button.view;

import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;

import org.hitogo.button.core.ButtonBuilder;

public interface ViewButtonBuilder extends ButtonBuilder<ViewButtonBuilder, ViewButton> {

    @NonNull
    ViewButtonBuilder addText(@IdRes @Nullable Integer viewId, @Nullable String text);

    @NonNull
    ViewButtonBuilder addText(@IdRes @Nullable Integer viewId, @StringRes int textRes);

    @NonNull
    ViewButtonBuilder setView(@IdRes int iconId);

    @NonNull
    ViewButtonBuilder setView(@IdRes int iconId, @IdRes @Nullable Integer clickId);

    @NonNull
    ViewButtonBuilder addDrawable(@DrawableRes int drawableRes);

    @NonNull
    ViewButtonBuilder addDrawable(@IdRes @Nullable Integer viewId, @DrawableRes int drawableRes);

    @NonNull
    ViewButtonBuilder addDrawable(@NonNull Drawable drawable);

    @NonNull
    ViewButtonBuilder addDrawable(@IdRes @Nullable Integer viewId, @Nullable Drawable drawable);
}

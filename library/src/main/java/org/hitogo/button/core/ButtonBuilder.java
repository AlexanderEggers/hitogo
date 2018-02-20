package org.hitogo.button.core;

import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;

public interface ButtonBuilder<C extends ButtonBuilder, B extends Button> {

    @NonNull
    C addText(@Nullable String text);

    @NonNull
    C addText(@StringRes int textRes);

    @NonNull
    C addText(@IdRes @Nullable Integer viewId, @Nullable String text);

    @NonNull
    C addText(@IdRes @Nullable Integer viewId, @StringRes int textRes);

    @NonNull
    <T> C setButtonListener(@Nullable ButtonListener<T> listener);

    @NonNull
    <T> C setButtonListener(@Nullable ButtonListener<T> listener, T buttonParameter);

    @NonNull
    <T> C setButtonListener(@Nullable ButtonListener<T> listener, boolean closeAfterClick);

    @NonNull
    <T> C setButtonListener(@Nullable ButtonListener<T> listener, boolean closeAfterClick, T buttonParameter);

    C addDrawable(@DrawableRes int drawableRes);

    C addDrawable(@IdRes @Nullable Integer viewId, @DrawableRes int drawableRes);

    C addDrawable(@NonNull Drawable drawable);

    C addDrawable(@IdRes @Nullable Integer viewId, @Nullable Drawable drawable);

    @NonNull
    B build();
}

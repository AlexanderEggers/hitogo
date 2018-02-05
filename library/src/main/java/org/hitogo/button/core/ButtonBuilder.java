package org.hitogo.button.core;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;

public interface ButtonBuilder<C extends ButtonBuilder, B extends Button> {

    @NonNull
    C setText(String text);

    @NonNull
    C setText(@StringRes int textRes);

    @NonNull
    <T> C setButtonListener(@Nullable ButtonListener<T> listener);

    @NonNull
    <T> C setButtonListener(@Nullable ButtonListener<T> listener, T buttonParameter);

    @NonNull
    <T> C setButtonListener(@Nullable ButtonListener<T> listener, boolean closeAfterClick);

    @NonNull
    <T> C setButtonListener(@Nullable ButtonListener<T> listener, boolean closeAfterClick, T buttonParameter);

    @NonNull
    B build();
}

package org.hitogo.alert.dialog;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.annotation.StyleRes;

import org.hitogo.alert.core.AlertBuilder;
import org.hitogo.button.core.Button;

public interface DialogAlertBuilder extends AlertBuilder<DialogAlertBuilder, DialogAlert> {

    @NonNull
    DialogAlertBuilder asDismissible();

    @NonNull
    DialogAlertBuilder asDismissible(boolean isDismissible);

    @NonNull
    DialogAlertBuilder asDismissible(@Nullable Button closeButton);

    @NonNull
    DialogAlertBuilder asSimpleDialog(@NonNull String title, @NonNull String text);

    @NonNull
    DialogAlertBuilder addButton(@NonNull String... buttonContent);

    @NonNull
    DialogAlertBuilder addButton(@StringRes int... buttonContent);

    @NonNull
    DialogAlertBuilder setStyle(@Nullable @StyleRes Integer dialogThemeResId);
}

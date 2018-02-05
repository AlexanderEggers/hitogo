package org.hitogo.alert.snackbar;

import android.content.res.ColorStateList;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;

import org.hitogo.alert.core.AlertBuilderBase;
import org.hitogo.button.core.Button;

public interface SnackbarAlertBuilder extends AlertBuilderBase<SnackbarAlertBuilder, SnackbarAlert> {

    @NonNull
    SnackbarAlertBuilder setText(CharSequence text);

    @NonNull
    SnackbarAlertBuilder setText(int resId);

    @NonNull
    SnackbarAlertBuilder addAction(Button button);

    @NonNull
    SnackbarAlertBuilder setActionTextColor(int color);

    @NonNull
    SnackbarAlertBuilder setActionTextColor(ColorStateList colors);

    @NonNull
    SnackbarAlertBuilder addCallback(Snackbar.Callback callback);

    @NonNull
    SnackbarAlertBuilder setDuration(int duration);
}

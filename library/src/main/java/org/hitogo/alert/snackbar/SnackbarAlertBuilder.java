package org.hitogo.alert.snackbar;

import android.content.res.ColorStateList;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;

import org.hitogo.alert.core.AlertBuilderBase;
import org.hitogo.button.core.Button;

public interface SnackbarAlertBuilder extends AlertBuilderBase<SnackbarAlertBuilder, SnackbarAlert> {

    @NonNull
    SnackbarAlertBuilder addAction(@NonNull Button button);

    @NonNull
    SnackbarAlertBuilder setActionTextColor(@ColorRes int color);

    @NonNull
    SnackbarAlertBuilder setActionTextColor(@NonNull ColorStateList colors);

    @NonNull
    SnackbarAlertBuilder addCallback(@NonNull Snackbar.Callback callback);

    @NonNull
    SnackbarAlertBuilder setDuration(int duration);
}

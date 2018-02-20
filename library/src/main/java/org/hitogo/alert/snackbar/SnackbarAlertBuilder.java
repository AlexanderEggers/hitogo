package org.hitogo.alert.snackbar;

import android.content.res.ColorStateList;
import android.support.annotation.ColorRes;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;

import org.hitogo.alert.core.Alert;
import org.hitogo.alert.core.AlertBuilderBase;
import org.hitogo.button.core.Button;

/**
 * Public api interface for the SnackbarAlertBuilderImpl. This interface includes all methods that
 * can be used by this builder.
 *
 * @see Alert
 * @since 1.0.0
 */
public interface SnackbarAlertBuilder extends AlertBuilderBase<SnackbarAlertBuilder, SnackbarAlert> {

    /**
     * Specifies the action for the snackbar. The action will only have a ButtonListener and a
     * text.
     *
     * @return Builder object which has called this method.
     * @since 1.0.0
     */
    @NonNull
    SnackbarAlertBuilder setAction(@NonNull Button button);

    @NonNull
    SnackbarAlertBuilder setActionTextColor(@ColorRes int color);

    @NonNull
    SnackbarAlertBuilder setActionTextColor(@NonNull ColorStateList colors);

    @NonNull
    SnackbarAlertBuilder addCallback(@NonNull Snackbar.Callback callback);

    @NonNull
    SnackbarAlertBuilder setDuration(@IntRange(from = -2) int duration);
}

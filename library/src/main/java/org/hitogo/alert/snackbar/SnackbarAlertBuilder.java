package org.hitogo.alert.snackbar;

import android.content.res.ColorStateList;
import android.support.annotation.ColorRes;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;

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

    /**
     * Specifies the text color for the action button which used by the snackbar.
     *
     * @param color a color resource reference
     * @return Builder object which has called this method.
     * @since 1.0.0
     */
    @NonNull
    SnackbarAlertBuilder setActionTextColor(@ColorRes int color);

    /**
     * Specifies the text color for the action button which used by the snackbar.
     *
     * @param colors a ColorStateList
     * @return Builder object which has called this method.
     * @since 1.0.0
     */
    @NonNull
    SnackbarAlertBuilder setActionTextColor(@NonNull ColorStateList colors);

    /**
     * Specifies the duration for the snackbar alert.
     *
     * @param duration an Int
     * @return Builder object which has called this method.
     * @since 1.0.0
     */
    @NonNull
    SnackbarAlertBuilder setDuration(@IntRange(from = -2) int duration);
}

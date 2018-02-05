package org.hitogo.alert.snackbar;

import android.support.annotation.NonNull;

import org.hitogo.alert.core.AlertBuilderBase;

public interface SnackbarAlertFactory<V extends AlertBuilderBase> {

    V asSnackbarAlert();
    V asSnackbarAlert(@NonNull Class<? extends SnackbarAlertImpl> targetClass);
    V asSnackbarAlert(@NonNull Class<? extends SnackbarAlertImpl> targetClass,
                      @NonNull Class<? extends SnackbarAlertParams> paramClass);
}

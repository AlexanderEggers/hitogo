package org.hitogo.alert.snackbar;

import android.support.annotation.NonNull;

import org.hitogo.alert.core.AlertBuilderBase;
import org.hitogo.alert.core.AlertImpl;
import org.hitogo.alert.core.AlertParams;

public interface SnackbarAlertFactory<V extends AlertBuilderBase> {

    V asSnackbarAlert();
    V asSnackbarAlert(@NonNull Class<? extends AlertImpl> targetClass);
    V asSnackbarAlert(@NonNull Class<? extends AlertImpl> targetClass,
                      @NonNull Class<? extends AlertParams> paramClass);
}

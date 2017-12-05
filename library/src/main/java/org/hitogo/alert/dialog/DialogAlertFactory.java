package org.hitogo.alert.dialog;

import android.support.annotation.NonNull;

import org.hitogo.alert.core.AlertBuilder;
import org.hitogo.alert.core.AlertImpl;
import org.hitogo.alert.core.AlertParams;

public interface DialogAlertFactory<D extends AlertBuilder> {

    D asDialogAlert();
    D asDialogAlert(@NonNull Class<? extends AlertImpl> targetClass);
    D asDialogAlert(@NonNull Class<? extends AlertImpl> targetClass,
                    @NonNull Class<? extends AlertParams> paramClass);
}

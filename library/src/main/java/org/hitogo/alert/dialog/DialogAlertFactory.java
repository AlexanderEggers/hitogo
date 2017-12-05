package org.hitogo.alert.dialog;

import android.support.annotation.NonNull;

import org.hitogo.alert.core.AlertBuilder;
import org.hitogo.alert.core.AlertImpl;
import org.hitogo.alert.core.AlertParams;

public interface DialogAlertFactory<D extends AlertBuilder> {

    D asDialog();
    D asDialog(@NonNull Class<? extends AlertImpl> targetClass);
    D asDialog(@NonNull Class<? extends AlertImpl> targetClass,
               @NonNull Class<? extends AlertParams> paramClass);
}

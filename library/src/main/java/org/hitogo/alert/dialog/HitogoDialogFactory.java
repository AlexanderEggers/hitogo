package org.hitogo.alert.dialog;

import android.support.annotation.NonNull;

import org.hitogo.alert.core.HitogoAlertBuilder;
import org.hitogo.alert.core.HitogoAlert;
import org.hitogo.alert.core.HitogoAlertParams;

public interface HitogoDialogFactory<D extends HitogoAlertBuilder> {

    D asDialog();
    D asDialog(@NonNull Class<? extends HitogoAlert> targetClass);
    D asDialog(@NonNull Class<? extends HitogoAlert> targetClass,
               @NonNull Class<? extends HitogoAlertParams> paramClass);
}

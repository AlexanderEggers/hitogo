package org.hitogo.dialog;

import android.support.annotation.NonNull;

import org.hitogo.core.HitogoBuilder;
import org.hitogo.core.HitogoObject;
import org.hitogo.core.HitogoParams;

public interface HitogoDialogFactory<D extends HitogoBuilder> {

    D asDialog();
    D asDialog(@NonNull Class<? extends HitogoObject> targetClass);
    D asDialog(@NonNull Class<? extends HitogoObject> targetClass,
               @NonNull Class<? extends HitogoParams> paramClass);
}

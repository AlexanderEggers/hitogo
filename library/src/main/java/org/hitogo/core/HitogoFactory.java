package org.hitogo.core;

import android.support.annotation.NonNull;

import org.hitogo.button.HitogoButtonObject;

@SuppressWarnings({"WeakerAccess", "unused"})
public interface HitogoFactory<V extends HitogoBuilder, D extends HitogoBuilder,
        B extends org.hitogo.button.HitogoBuilder> {

    V asView();
    V asView(@NonNull Class<? extends HitogoObject> targetClass);
    V asView(@NonNull Class<? extends HitogoObject> targetClass,
             @NonNull Class<? extends HitogoParams> paramClass);


    D asDialog();
    D asDialog(@NonNull Class<? extends HitogoObject> targetClass);
    D asDialog(@NonNull Class<? extends HitogoObject> targetClass,
               @NonNull Class<? extends HitogoParams> paramClass);


    B asButton();
    B asButton(@NonNull Class<? extends HitogoButtonObject> targetClass);
    B asButton(@NonNull Class<? extends HitogoButtonObject> targetClass,
               @NonNull Class<? extends org.hitogo.button.HitogoParams> paramClass);
}

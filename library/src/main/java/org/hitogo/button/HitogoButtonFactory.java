package org.hitogo.button;

import android.support.annotation.NonNull;

public interface HitogoButtonFactory<B extends org.hitogo.button.HitogoBuilder> {

    B asButton();
    B asButton(@NonNull Class<? extends HitogoButtonObject> targetClass);
    B asButton(@NonNull Class<? extends HitogoButtonObject> targetClass,
               @NonNull Class<? extends org.hitogo.button.HitogoParams> paramClass);
}

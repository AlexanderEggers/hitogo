package org.hitogo.view;

import android.support.annotation.NonNull;

import org.hitogo.core.HitogoBuilder;
import org.hitogo.core.HitogoObject;
import org.hitogo.core.HitogoParams;

public interface HitogoViewFactory<V extends HitogoBuilder> {

    V asView();
    V asView(@NonNull Class<? extends HitogoObject> targetClass);
    V asView(@NonNull Class<? extends HitogoObject> targetClass,
             @NonNull Class<? extends HitogoParams> paramClass);
}

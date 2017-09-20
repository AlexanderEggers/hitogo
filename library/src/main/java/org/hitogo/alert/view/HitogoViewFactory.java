package org.hitogo.alert.view;

import android.support.annotation.NonNull;

import org.hitogo.alert.core.HitogoAlertBuilder;
import org.hitogo.alert.core.HitogoAlert;
import org.hitogo.alert.core.HitogoAlertParams;

public interface HitogoViewFactory<V extends HitogoAlertBuilder> {

    V asView();
    V asView(@NonNull Class<? extends HitogoAlert> targetClass);
    V asView(@NonNull Class<? extends HitogoAlert> targetClass,
             @NonNull Class<? extends HitogoAlertParams> paramClass);
}

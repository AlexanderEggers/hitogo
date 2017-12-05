package org.hitogo.alert.view;

import android.support.annotation.NonNull;

import org.hitogo.alert.core.AlertBuilder;
import org.hitogo.alert.core.AlertImpl;
import org.hitogo.alert.core.AlertParams;

public interface ViewAlertFactory<V extends AlertBuilder> {

    V asView();
    V asView(@NonNull Class<? extends AlertImpl> targetClass);
    V asView(@NonNull Class<? extends AlertImpl> targetClass,
             @NonNull Class<? extends AlertParams> paramClass);
}

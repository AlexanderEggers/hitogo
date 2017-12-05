package org.hitogo.alert.popup;

import android.support.annotation.NonNull;

import org.hitogo.alert.core.AlertImpl;
import org.hitogo.alert.core.AlertBuilder;
import org.hitogo.alert.core.AlertParams;

public interface PopupAlertFactory<V extends AlertBuilder> {

    V asPopupAlert();
    V asPopupAlert(@NonNull Class<? extends AlertImpl> targetClass);
    V asPopupAlert(@NonNull Class<? extends AlertImpl> targetClass,
                   @NonNull Class<? extends AlertParams> paramClass);
}

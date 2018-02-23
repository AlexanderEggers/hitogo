package org.hitogo.alert.toast;

import android.support.annotation.NonNull;

import org.hitogo.alert.core.AlertBuilderBase;
import org.hitogo.alert.core.AlertImpl;
import org.hitogo.alert.core.AlertParams;

public interface ToastAlertFactory<V extends AlertBuilderBase> {

    V asToastAlert();

    V asToastAlert(@NonNull Class<? extends AlertImpl> targetClass);

    V asToastAlert(@NonNull Class<? extends AlertImpl> targetClass,
                   @NonNull Class<? extends AlertParams> paramClass);
}

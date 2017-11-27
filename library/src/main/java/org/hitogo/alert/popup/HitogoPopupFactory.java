package org.hitogo.alert.popup;

import android.support.annotation.NonNull;

import org.hitogo.alert.core.HitogoAlert;
import org.hitogo.alert.core.HitogoAlertBuilder;
import org.hitogo.alert.core.HitogoAlertParams;

public interface HitogoPopupFactory<V extends HitogoAlertBuilder> {

    V asPopup();
    V asPopup(@NonNull Class<? extends HitogoAlert> targetClass);
    V asPopup(@NonNull Class<? extends HitogoAlert> targetClass,
             @NonNull Class<? extends HitogoAlertParams> paramClass);
}

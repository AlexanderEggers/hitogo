package org.hitogo.alert.view.anim;

import android.support.annotation.NonNull;
import android.view.View;

import org.hitogo.alert.core.AlertImpl;
import org.hitogo.alert.view.ViewAlertParams;

@SuppressWarnings({"WeakerAccess"})
public abstract class Animation {

    public abstract void showAnimation(@NonNull final ViewAlertParams params, @NonNull final View hitogoView,
                                       @NonNull final AlertImpl alert);

    public abstract void hideAnimation(@NonNull final ViewAlertParams params, @NonNull final View hitogoView,
                                       @NonNull final AlertImpl alert);

    public abstract long getAnimationDuration();
}

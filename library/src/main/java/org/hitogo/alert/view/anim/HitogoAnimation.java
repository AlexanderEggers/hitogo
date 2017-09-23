package org.hitogo.alert.view.anim;

import android.support.annotation.NonNull;
import android.view.View;

import org.hitogo.alert.core.HitogoAlert;
import org.hitogo.alert.view.HitogoViewParams;

@SuppressWarnings({"WeakerAccess"})
public abstract class HitogoAnimation {

    public abstract void showAnimation(@NonNull final HitogoViewParams params, @NonNull final View hitogoView,
                                       @NonNull final HitogoAlert hitogoAlert);

    public abstract void hideAnimation(@NonNull final HitogoViewParams params, @NonNull final View hitogoView,
                                       @NonNull final HitogoAlert hitogoAlert);

    public abstract long getAnimationDuration();
}

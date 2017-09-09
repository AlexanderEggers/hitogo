package org.hitogo.core;

import android.support.annotation.NonNull;
import android.view.View;

import org.hitogo.view.HitogoViewParams;

@SuppressWarnings({"WeakerAccess", "unused"})
public abstract class HitogoAnimation {

    public abstract void showAnimation(@NonNull final HitogoViewParams params, @NonNull final View hitogoView,
                                       @NonNull final HitogoObject hitogoObject);

    public abstract void hideAnimation(@NonNull final HitogoViewParams params, @NonNull final View hitogoView,
                                       @NonNull final HitogoObject hitogoObject);

    public void onFinishShow(HitogoObject object) {
        //implementation optional
    }

    public void onFinishHide(HitogoObject object) {
        //implementation optional
    }

    public abstract long getAnimationDuration();
}

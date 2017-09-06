package org.hitogo.view;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewManager;

import org.hitogo.core.HitogoObject;

@SuppressWarnings({"WeakerAccess", "unused"})
public abstract class HitogoAnimation {

    public abstract void showAnimation(@NonNull final View hitogoView, final HitogoObject hitogoObject);
    protected abstract void hideAnimation(@NonNull final View hitogoView, @NonNull final HitogoObject hitogoObject,
                                          @NonNull ViewManager manager);
    protected abstract long getAnimationDuration();
}

package org.hitogo.core;

import android.app.Activity;
import android.arch.lifecycle.LifecycleOwner;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

@SuppressWarnings({"WeakerAccess", "unused"})
public interface HitogoContainer extends LifecycleOwner {
    @NonNull HitogoController getController();
    @Nullable View getView();
    @NonNull Activity getActivity();
    @Nullable View getActivityView();
}

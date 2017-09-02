package com.mordag.hitogo;

import android.app.Activity;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

@SuppressWarnings({"WeakerAccess", "unused"})
public interface HitogoContainer extends LifecycleRegistryOwner {
    @NonNull HitogoController getController();
    @Nullable View getView();
    @NonNull Activity getActivity();
    @NonNull HitogoController initialiseHitogo();
}

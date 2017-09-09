package org.hitogo.core;

import android.app.Activity;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

@SuppressWarnings({"WeakerAccess", "unused"})
public interface HitogoContainer extends LifecycleRegistryOwner {
    @NonNull HitogoController getController();
    @NonNull HitogoController initialiseHitogo(@NonNull LifecycleRegistry lifecycle);
}

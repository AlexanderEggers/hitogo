package com.mordag.hitogo;

import android.arch.lifecycle.LifecycleRegistry;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

@SuppressWarnings({"WeakerAccess", "unused"})
public abstract class HitogoFragment extends Fragment implements HitogoContainer {

    private HitogoController hitogoController;
    private LifecycleRegistry lifecycle = new LifecycleRegistry(this);

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        hitogoController = initialiseHitogo();
    }

    @NonNull
    @Override
    public HitogoController getController() {
        return hitogoController;
    }

    @Override
    public LifecycleRegistry getLifecycle() {
        return lifecycle;
    }
}

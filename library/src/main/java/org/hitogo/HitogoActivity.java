package org.hitogo;

import android.app.Activity;
import android.arch.lifecycle.LifecycleRegistry;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

@SuppressWarnings({"WeakerAccess", "unused"})
public abstract class HitogoActivity extends AppCompatActivity implements HitogoContainer {

    private HitogoController hitogoController;
    private LifecycleRegistry lifecycle = new LifecycleRegistry(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hitogoController = initialiseHitogo(lifecycle);
    }

    @NonNull
    @Override
    public HitogoController getController() {
        return hitogoController;
    }

    @Nullable
    @Override
    public View getView() {
        return getWindow().getDecorView().getRootView();
    }

    @NonNull
    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public LifecycleRegistry getLifecycle() {
        return lifecycle;
    }
}

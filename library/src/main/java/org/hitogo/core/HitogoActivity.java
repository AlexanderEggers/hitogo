package org.hitogo.core;

import android.app.Activity;
import android.arch.lifecycle.Lifecycle;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

@SuppressWarnings({"WeakerAccess", "unused"})
public abstract class HitogoActivity extends AppCompatActivity implements HitogoContainer {

    private HitogoController hitogoController;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hitogoController = initialiseHitogo(getLifecycle());
    }

    @NonNull
    @Override
    public HitogoController getController() {
        return hitogoController;
    }

    @Nullable
    @Override
    public View getView() {
        return getWindow().getDecorView().findViewById(android.R.id.content);
    }

    @NonNull
    @Override
    public Activity getActivity() {
        return this;
    }

    @Nullable
    @Override
    public View getActivityView() {
        return getView();
    }

    @NonNull
    public abstract HitogoController initialiseHitogo(@NonNull Lifecycle lifecycle);
}

package org.hitogo.core;

import android.app.Activity;
import android.arch.lifecycle.Lifecycle;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

@SuppressWarnings({"WeakerAccess", "unused"})
public abstract class HitogoActivity<T extends HitogoController> extends AppCompatActivity implements HitogoContainer {

    private T hitogoController;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hitogoController = initialiseController(getLifecycle());
    }

    @NonNull
    @Override
    public T getController() {
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
    public abstract T initialiseController(@NonNull Lifecycle lifecycle);
}

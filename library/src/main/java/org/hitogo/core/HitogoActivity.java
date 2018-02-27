package org.hitogo.core;

import android.app.Activity;
import android.arch.lifecycle.Lifecycle;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Fully implemented activity that is using the HitogoContainer. This class can be used to have
 * certain values pre-defined.
 *
 * @param <T> Class which is extending HitogoController
 * @see HitogoController
 * @since 1.0.0
 */
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

    /**
     * Creates a new HitogoController using the given Lifecycle object.
     *
     * @param lifecycle Lifecycle object provided for the HitogoController creation
     * @return a new HitogoController
     * @see HitogoController
     * @since 1.0.0
     */
    @NonNull
    public abstract T initialiseController(@NonNull Lifecycle lifecycle);
}

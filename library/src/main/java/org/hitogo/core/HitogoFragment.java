package org.hitogo.core;

import android.arch.lifecycle.Lifecycle;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

@SuppressWarnings({"WeakerAccess", "unused"})
public abstract class HitogoFragment<T extends HitogoController> extends Fragment implements HitogoContainer {

    private T hitogoController;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        hitogoController = initialiseController(getLifecycle());
    }

    @NonNull
    @Override
    public T getController() {
        return hitogoController;
    }

    @Nullable
    @Override
    public View getActivityView() {
        return getActivity().getWindow().getDecorView().findViewById(android.R.id.content);
    }

    @NonNull
    public abstract T initialiseController(@NonNull Lifecycle lifecycle);
}

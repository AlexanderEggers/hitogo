package org.hitogo.core;

import android.arch.lifecycle.Lifecycle;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

@SuppressWarnings({"WeakerAccess", "unused"})
public abstract class HitogoFragment extends Fragment implements HitogoContainer {

    private HitogoController hitogoController;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        hitogoController = initialiseHitogo(getLifecycle());
    }

    @NonNull
    @Override
    public HitogoController getController() {
        return hitogoController;
    }

    @Nullable
    @Override
    public View getActivityView() {
        return getActivity().getWindow().getDecorView().findViewById(android.R.id.content);
    }

    @NonNull
    public abstract HitogoController initialiseHitogo(@NonNull Lifecycle lifecycle);
}

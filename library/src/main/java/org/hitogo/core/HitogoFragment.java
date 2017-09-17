package org.hitogo.core;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

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
}

package com.mordag.crouton;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

public abstract class CroutonFragment extends Fragment implements CroutonContainer {

    protected CroutonController croutonController;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        croutonController = initialiseController();
    }

    @NonNull
    @Override
    public CroutonController getController() {
        return croutonController;
    }
}

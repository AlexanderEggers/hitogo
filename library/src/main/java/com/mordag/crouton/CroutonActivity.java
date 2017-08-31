package com.mordag.crouton;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public abstract class CroutonActivity extends AppCompatActivity implements CroutonContainer {

    protected CroutonController croutonController;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        croutonController = initialiseController();
    }

    @NonNull
    @Override
    public CroutonController getController() {
        return croutonController;
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
}

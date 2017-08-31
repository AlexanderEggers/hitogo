package com.mordag.crouton;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

public interface CroutonContainer {
    @NonNull CroutonController getController();
    @Nullable View getView();
    @NonNull Activity getActivity();
    CroutonController initialiseController();
}

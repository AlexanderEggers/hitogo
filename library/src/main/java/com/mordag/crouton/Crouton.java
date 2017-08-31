package com.mordag.crouton;

import android.support.annotation.NonNull;

public final class Crouton {

    private Crouton() {
        throw new IllegalStateException("Methods can only access by static methods.");
    }

    @NonNull
    public static CroutonBuilder with(@NonNull CroutonContainer container) {
        return new CroutonBuilder(container.getActivity(), container.getView(),
                container.getController());
    }
}

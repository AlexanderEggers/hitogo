package org.hitogo.button.core;

import android.support.annotation.NonNull;

import org.hitogo.core.HitogoController;

@SuppressWarnings({"WeakerAccess", "unused"})
public abstract class ButtonLifecycle<T extends ButtonParams> {

    protected void onCheck(@NonNull T params) {

    }

    protected void onCheck(@NonNull HitogoController controller, @NonNull T params) {

    }

    protected void onCreate(@NonNull T params) {

    }
}

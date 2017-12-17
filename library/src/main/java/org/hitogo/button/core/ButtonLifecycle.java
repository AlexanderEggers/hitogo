package org.hitogo.button.core;

import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;

import org.hitogo.core.HitogoController;

@SuppressWarnings({"WeakerAccess", "unused"})
public abstract class ButtonLifecycle<T extends ButtonParams> {

    @CallSuper
    protected void onCheck(@NonNull T params) {

    }

    @CallSuper
    protected void onCheck(@NonNull HitogoController controller, @NonNull T params) {

    }

    @CallSuper
    protected void onCreate(@NonNull T params) {

    }

    @CallSuper
    protected void onCreate(@NonNull HitogoController controller, @NonNull T params) {

    }
}

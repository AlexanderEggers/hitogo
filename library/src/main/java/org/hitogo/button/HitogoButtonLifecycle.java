package org.hitogo.button;

import android.support.annotation.NonNull;

import org.hitogo.core.HitogoController;

@SuppressWarnings({"WeakerAccess", "unused"})
public abstract class HitogoButtonLifecycle<T extends HitogoParams> {

    protected void onCheck(@NonNull T params) {

    }

    protected void onCheck(@NonNull HitogoController controller, @NonNull T params) {

    }

    protected void onCreate(@NonNull T params) {

    }
}

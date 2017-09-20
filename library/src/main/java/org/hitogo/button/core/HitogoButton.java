package org.hitogo.button.core;

import android.support.annotation.NonNull;

import org.hitogo.core.HitogoController;

@SuppressWarnings({"WeakerAccess", "unused"})
public abstract class HitogoButton<T extends HitogoButtonParams> extends HitogoButtonLifecycle<T> {

    private T params;

    final HitogoButton<T> buildButton(@NonNull HitogoController controller, @NonNull T params) {
        this.params = params;

        onCheck(params);
        onCheck(controller, params);
        onCreate(params);

        return this;
    }

    public final T getParams() {
        return params;
    }
}

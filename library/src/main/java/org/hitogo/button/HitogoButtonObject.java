package org.hitogo.button;

import android.support.annotation.NonNull;

import org.hitogo.core.HitogoController;

@SuppressWarnings({"WeakerAccess", "unused"})
public abstract class HitogoButtonObject<T extends HitogoParams> extends HitogoButtonLifecycle<T> {

    private T params;

    final HitogoButtonObject<T> buildButton(@NonNull HitogoController controller, @NonNull T params)
            throws IllegalAccessException {
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

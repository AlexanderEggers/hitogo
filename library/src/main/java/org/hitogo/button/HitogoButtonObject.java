package org.hitogo.button;

import android.support.annotation.NonNull;

@SuppressWarnings({"WeakerAccess", "unused"})
public abstract class HitogoButtonObject<T extends HitogoParams> extends HitogoButtonLifecycle<T> {

    private T params;

    public final HitogoButtonObject<T> buildButton(@NonNull T params)
            throws IllegalAccessException {
        onCheck(params);
        onCreate(params);
        return this;
    }

    public final T getParams() {
        return params;
    }
}

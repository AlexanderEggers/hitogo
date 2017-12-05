package org.hitogo.button.core;

import android.support.annotation.NonNull;

import org.hitogo.BuildConfig;
import org.hitogo.core.HitogoContainer;
import org.hitogo.core.HitogoController;

import java.lang.ref.WeakReference;

@SuppressWarnings({"WeakerAccess", "unused"})
public abstract class ButtonImpl<T extends ButtonParams> extends ButtonLifecycle<T> implements Button<T> {

    private WeakReference<HitogoContainer> containerRef;
    private T params;

    final ButtonImpl<T> create(@NonNull HitogoContainer container, @NonNull T params) {
        this.containerRef = new WeakReference<>(container);
        this.params = params;

        if(BuildConfig.DEBUG) {
            onCheck(params);
            onCheck(getController(), params);
        }

        onCreate(params);
        return this;
    }

    @NonNull
    public final T getParams() {
        return params;
    }

    @NonNull
    protected final HitogoContainer getContainer() {
        return containerRef.get();
    }

    @NonNull
    protected final HitogoController getController() {
        return containerRef.get().getController();
    }
}

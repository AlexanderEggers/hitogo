package org.hitogo.button.core;

import android.support.annotation.NonNull;

import org.hitogo.BuildConfig;
import org.hitogo.core.HitogoContainer;
import org.hitogo.core.HitogoController;

import java.lang.ref.WeakReference;

@SuppressWarnings({"WeakerAccess", "unused"})
public abstract class HitogoButton<T extends HitogoButtonParams> extends HitogoButtonLifecycle<T> {

    private WeakReference<HitogoContainer> containerRef;
    private T params;

    final HitogoButton<T> create(@NonNull HitogoContainer container, @NonNull T params) {
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
    public final HitogoContainer getContainer() {
        return containerRef.get();
    }

    @NonNull
    public final HitogoController getController() {
        return containerRef.get().getController();
    }

    @NonNull
    public final T getParams() {
        return params;
    }
}

package org.hitogo.button.core;

import android.support.annotation.NonNull;

import org.hitogo.core.HitogoContainer;
import org.hitogo.core.HitogoController;

import java.lang.ref.WeakReference;

@SuppressWarnings({"WeakerAccess", "unused"})
public abstract class ButtonImpl<T extends ButtonParams> extends ButtonLifecycle<T> implements Button<T> {

    private WeakReference<HitogoContainer> containerRef;
    private T params;

    ButtonImpl<T> create(@NonNull HitogoContainer container, @NonNull T params) {
        this.containerRef = new WeakReference<>(container);
        this.params = params;

        if(getController().provideIsDebugState()) {
            onCheck(params);
        }

        onCreate(params);
        return this;
    }

    @NonNull
    public HitogoContainer getContainer() {
        return containerRef.get();
    }

    @NonNull
    public HitogoController getController() {
        return containerRef.get().getController();
    }

    @NonNull
    public T getParams() {
        return params;
    }
}

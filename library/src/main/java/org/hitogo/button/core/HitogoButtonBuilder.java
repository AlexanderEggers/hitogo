package org.hitogo.button.core;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;

import org.hitogo.core.HitogoContainer;
import org.hitogo.core.HitogoController;

import java.lang.ref.WeakReference;

@SuppressWarnings({"WeakerAccess", "unused"})
public abstract class HitogoButtonBuilder {

    private Class<? extends HitogoButton> targetClass;
    private Class<? extends HitogoButtonParams> paramClass;
    private WeakReference<HitogoContainer> containerRef;

    private HitogoButtonParamsHolder holder = new HitogoButtonParamsHolder();

    public HitogoButtonBuilder(@NonNull Class<? extends HitogoButton> targetClass,
                               @NonNull Class<? extends HitogoButtonParams> paramClass,
                               @NonNull HitogoContainer container) {
        this.targetClass = targetClass;
        this.paramClass = paramClass;
        this.containerRef = new WeakReference<>(container);
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public final HitogoButton build() {
        onProvideData(holder);

        try {
            HitogoButton object = targetClass.getConstructor().newInstance();
            HitogoButtonParams params = paramClass.getConstructor().newInstance();
            params.provideData(holder, new Bundle());
            object.buildButton(getController(), params);
            return object;
        } catch (Exception e) {
            Log.wtf(HitogoButtonBuilder.class.getName(), "Build process failed.");
            throw new IllegalStateException(e);
        }
    }

    protected abstract void onProvideData(HitogoButtonParamsHolder holder);

    protected final HitogoContainer getContainer() {
        return containerRef.get();
    }

    protected final HitogoController getController() {
        return containerRef.get().getController();
    }
}

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

    private Bundle privateBundle = new Bundle();
    private HitogoButtonType builderType;

    public HitogoButtonBuilder(@NonNull Class<? extends HitogoButton> targetClass,
                               @NonNull Class<? extends HitogoButtonParams> paramClass,
                               @NonNull HitogoContainer container, HitogoButtonType builderType) {
        this.targetClass = targetClass;
        this.paramClass = paramClass;
        this.containerRef = new WeakReference<>(container);
        this.builderType = builderType;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public final HitogoButton build() {
        onProvideData(holder);
        onProvidePrivateData(holder);

        try {
            HitogoButton object = targetClass.getConstructor().newInstance();
            HitogoButtonParams params = paramClass.getConstructor().newInstance();
            params.provideData(holder, privateBundle);
            object.create(getContainer(), params);
            return object;
        } catch (Exception e) {
            Log.wtf(HitogoButtonBuilder.class.getName(), "Build process failed.");
            throw new IllegalStateException(e);
        }
    }

    private void onProvidePrivateData(HitogoButtonParamsHolder holder) {
        privateBundle.putSerializable("type", builderType);
    }

    protected abstract void onProvideData(HitogoButtonParamsHolder holder);

    protected final HitogoContainer getContainer() {
        return containerRef.get();
    }

    protected final HitogoController getController() {
        return containerRef.get().getController();
    }
}

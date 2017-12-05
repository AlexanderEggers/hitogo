package org.hitogo.button.core;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;

import org.hitogo.core.HitogoContainer;
import org.hitogo.core.HitogoController;

import java.lang.ref.WeakReference;

@SuppressWarnings({"WeakerAccess", "unused"})
public abstract class ButtonBuilder<B extends Button> {

    private Class<? extends ButtonImpl> targetClass;
    private Class<? extends ButtonParams> paramClass;
    private WeakReference<HitogoContainer> containerRef;

    private ButtonParamsHolder holder = new ButtonParamsHolder();

    private Bundle privateBundle = new Bundle();
    private ButtonType builderType;

    public ButtonBuilder(@NonNull Class<? extends ButtonImpl> targetClass,
                         @NonNull Class<? extends ButtonParams> paramClass,
                         @NonNull HitogoContainer container, ButtonType builderType) {
        this.targetClass = targetClass;
        this.paramClass = paramClass;
        this.containerRef = new WeakReference<>(container);
        this.builderType = builderType;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public final B build() {
        onProvideData(holder);
        onProvidePrivateData(holder);

        try {
            ButtonImpl object = targetClass.getConstructor().newInstance();
            ButtonParams params = paramClass.getConstructor().newInstance();
            params.provideData(holder, privateBundle);
            return (B) object.create(getContainer(), params);
        } catch (Exception e) {
            Log.wtf(ButtonBuilder.class.getName(), "Build process failed.");
            throw new IllegalStateException(e);
        }
    }

    private void onProvidePrivateData(ButtonParamsHolder holder) {
        privateBundle.putSerializable(ButtonParamsKeys.TYPE_KEY, builderType);
    }

    protected abstract void onProvideData(ButtonParamsHolder holder);

    protected final HitogoContainer getContainer() {
        return containerRef.get();
    }

    protected final HitogoController getController() {
        return containerRef.get().getController();
    }
}

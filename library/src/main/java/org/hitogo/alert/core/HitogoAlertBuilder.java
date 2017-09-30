package org.hitogo.alert.core;

import android.arch.lifecycle.Lifecycle;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;

import org.hitogo.alert.view.HitogoViewBuilder;
import org.hitogo.core.HitogoContainer;
import org.hitogo.core.HitogoController;

import java.lang.ref.WeakReference;

@SuppressWarnings({"WeakerAccess", "unused", "unchecked"})
public abstract class HitogoAlertBuilder<T> {

    private static final int DEFAULT_SHOW_DELAY_IN_MS = 1000;

    private Class<? extends HitogoAlert> targetClass;
    private Class<? extends HitogoAlertParams> paramClass;
    private WeakReference<HitogoContainer> containerRef;
    private HitogoController controller;
    private HitogoVisibilityListener visibilityListener;

    private HitogoAlertParamsHolder holder = new HitogoAlertParamsHolder();

    private Bundle privateBundle = new Bundle();
    private Bundle arguments;
    private Integer state;
    private String tag;
    private int hashCode;
    private HitogoAlertType builderType;

    public HitogoAlertBuilder(@NonNull Class<? extends HitogoAlert> targetClass,
                              @NonNull Class<? extends HitogoAlertParams> paramClass,
                              @NonNull HitogoContainer container, @NonNull HitogoAlertType builderType) {
        this.targetClass = targetClass;
        this.paramClass = paramClass;
        this.containerRef = new WeakReference<>(container);
        this.controller = container.getController();
        this.builderType = builderType;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public final HitogoAlert build() {
        if(tag != null) {
            hashCode = tag.hashCode();
        } else {
            tag = "";
            hashCode = tag.hashCode();
        }

        onProvideData(holder);
        onProvidePrivateData(holder);

        try {
            HitogoAlert object = targetClass.getConstructor().newInstance();
            HitogoAlertParams params = paramClass.getConstructor().newInstance();
            params.provideData(holder, privateBundle);
            object.startHitogo(getContainer(), params);
            return object;
        } catch (Exception e) {
            Log.wtf(HitogoViewBuilder.class.getName(), "Build process failed.");
            throw new IllegalStateException(e);
        }
    }

    private void onProvidePrivateData(HitogoAlertParamsHolder holder) {
        privateBundle.putString("tag", tag);
        privateBundle.putInt("hashCode", hashCode);
        privateBundle.putBundle("arguments", arguments);
        privateBundle.putSerializable("type", builderType);
        privateBundle.putSerializable("state", state);

        holder.provideVisibilityListener(visibilityListener);
    }

    protected abstract void onProvideData(HitogoAlertParamsHolder holder);

    @NonNull
    public final T controlledBy(HitogoController controller) {
        this.controller = controller;
        return (T) this;
    }

    @NonNull
    public final T setBundle(@NonNull Bundle arguments) {
        this.arguments = arguments;
        return (T) this;
    }

    @NonNull
    public final T setTag(@NonNull String tag) {
        this.tag = tag;
        return (T) this;
    }

    @NonNull
    public final T withState(Integer state) {
        this.state = state;
        return (T) this;
    }

    @NonNull
    public final T withState(Enum state) {
        this.state = state != null ? state.ordinal() : null;
        return (T) this;
    }

    @NonNull
    public final T addVisibilityListener(@NonNull HitogoVisibilityListener visibilityListener) {
        this.visibilityListener = visibilityListener;
        return (T) this;
    }

    public final void show() {
        HitogoContainer container = containerRef.get();
        if(container.getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
            build().show();
        } else {
            build().showDelayed(HitogoAlert.DEFAULT_SHOW_DELAY_IN_MS);
        }
    }

    public final void showDelayed(long millis) {
        build().showDelayed(millis);
    }

    protected final HitogoContainer getContainer() {
        return containerRef.get();
    }

    protected final HitogoController getController() {
        return controller;
    }
}

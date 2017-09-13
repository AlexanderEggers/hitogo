package org.hitogo.core;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;

import org.hitogo.view.HitogoViewBuilder;

import java.lang.ref.WeakReference;

@SuppressWarnings({"WeakerAccess", "unused", "unchecked"})
public abstract class HitogoBuilder<T> {

    private Class<? extends HitogoObject> targetClass;
    private Class<? extends HitogoParams> paramClass;
    private WeakReference<HitogoContainer> containerRef;
    private HitogoController controller;

    private HitogoParamsHolder holder = new HitogoParamsHolder();

    private Bundle arguments;
    private String tag;
    private HitogoObject.HitogoType builderType;
    private boolean closeOthers = true;

    public HitogoBuilder(@NonNull Class<? extends HitogoObject> targetClass,
                         @NonNull Class<? extends HitogoParams> paramClass,
                         @NonNull HitogoContainer container, @NonNull HitogoObject.HitogoType builderType) {
        this.targetClass = targetClass;
        this.paramClass = paramClass;
        this.containerRef = new WeakReference<>(container);
        this.controller = container.getController();
        this.builderType = builderType;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public final HitogoObject build(String tag) {
        this.tag = tag;
        onProvideData(holder);

        try {
            HitogoObject object = targetClass.getConstructor().newInstance();
            HitogoParams params = paramClass.getConstructor().newInstance();
            params.provideData(holder, createPrivateBundle());
            object.startHitogo(containerRef.get(), params);
            return object;
        } catch (Exception e) {
            Log.wtf(HitogoViewBuilder.class.getName(), "Build process failed.");
            throw new IllegalStateException(e);
        }
    }

    private Bundle createPrivateBundle() {
        Bundle privateBundle = new Bundle();
        privateBundle.putString("tag", tag);
        privateBundle.putInt("hashCode", tag.hashCode());
        privateBundle.putBoolean("closeOthers", closeOthers);
        privateBundle.putBundle("arguments", arguments);
        privateBundle.putSerializable("type", builderType);
        return privateBundle;
    }

    protected abstract void onProvideData(HitogoParamsHolder holder);

    @NonNull
    public final T closeOthers(@NonNull String text) {
        this.closeOthers = true;
        return (T) this;
    }

    @NonNull
    public final T allowOthers() {
        this.closeOthers = false;
        return (T) this;
    }

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

    public final void show(@NonNull String tag) {
        build(tag).show();
    }

    public final void showDelayed(@NonNull String tag, long millis) {
        HitogoContainer container = containerRef.get();
        if (container instanceof Fragment) {
            internalShowDelayed(container.getActivity(), (Fragment) container, tag, millis);
        } else {
            internalShowDelayed(container.getActivity(), null, tag, millis);
        }
    }

    private void internalShowDelayed(@NonNull final Activity activity,
                                     @Nullable final Fragment fragment, final String tag, long millis) {

        if (millis == 0) {
            Log.i(HitogoBuilder.class.getName(), "Delayed is not executed. Reason: delay in milliseconds == 0.");
            build(tag).show();
        } else {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if ((fragment != null && fragment.isAdded()) || !activity.isFinishing()) {
                        build(tag).show();
                    }
                }
            }, millis);
        }
    }

    protected final HitogoContainer getContainer() {
        return containerRef.get();
    }

    protected final HitogoController getController() {
        return controller;
    }
}

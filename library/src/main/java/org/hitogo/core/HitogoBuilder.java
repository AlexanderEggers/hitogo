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
    private HitogoVisibilityListener visibilityListener;

    private HitogoParamsHolder holder = new HitogoParamsHolder();

    private Bundle privateBundle = new Bundle();
    private Bundle arguments;
    private Integer state;
    private String tag;
    private int hashCode;
    private HitogoObject.HitogoType builderType;

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
    public final HitogoObject build() {
        if(tag != null) {
            hashCode = tag.hashCode();
        } else {
            tag = "";
            hashCode = tag.hashCode();
        }

        onProvideData(holder);
        onProvidePrivateData(holder);

        try {
            HitogoObject object = targetClass.getConstructor().newInstance();
            HitogoParams params = paramClass.getConstructor().newInstance();
            params.provideData(holder, privateBundle);
            object.startHitogo(containerRef.get(), params);
            return object;
        } catch (Exception e) {
            Log.wtf(HitogoViewBuilder.class.getName(), "Build process failed.");
            throw new IllegalStateException(e);
        }
    }

    private void onProvidePrivateData(HitogoParamsHolder holder) {
        privateBundle.putString("tag", tag);
        privateBundle.putInt("hashCode", hashCode);
        privateBundle.putBundle("arguments", arguments);
        privateBundle.putSerializable("type", builderType);
        privateBundle.putSerializable("state", state);

        holder.provideVisibilityListener(visibilityListener);
    }

    protected abstract void onProvideData(HitogoParamsHolder holder);

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
        build().show();
    }

    public final void showDelayed(long millis) {
        HitogoContainer container = containerRef.get();
        if (container instanceof Fragment) {
            internalShowDelayed(container.getActivity(), (Fragment) container, millis);
        } else {
            internalShowDelayed(container.getActivity(), null, millis);
        }
    }

    private void internalShowDelayed(@NonNull final Activity activity,
                                     @Nullable final Fragment fragment, long millis) {

        if (millis == 0) {
            Log.i(HitogoBuilder.class.getName(), "Delayed is not executed. Reason: delay in milliseconds == 0.");
            build().show();
        } else {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if ((fragment != null && fragment.isAdded()) || !activity.isFinishing()) {
                        build().show();
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

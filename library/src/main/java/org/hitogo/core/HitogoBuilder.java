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

@SuppressWarnings({"WeakerAccess", "unused"})
public abstract class HitogoBuilder {

    private Class<? extends HitogoObject> targetClass;
    private Class<? extends HitogoParams> paramClass;
    private WeakReference<HitogoContainer> containerRef;

    private HitogoParamsHolder holder = new HitogoParamsHolder();

    private String tag;
    private HitogoObject.HitogoType builderType;

    public HitogoBuilder(@NonNull Class<? extends HitogoObject> targetClass,
                         @NonNull Class<? extends HitogoParams> paramClass,
                         @NonNull HitogoContainer container, @NonNull HitogoObject.HitogoType builderType) {
        this.targetClass = targetClass;
        this.paramClass = paramClass;
        this.containerRef = new WeakReference<>(container);
        this.builderType = builderType;
    }

    @NonNull
    public final HitogoObject build() {
        Bundle privateBundle = createPrivateBundle();

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

    private Bundle createPrivateBundle() {
        Bundle privateBundle = new Bundle();
        privateBundle.putString("tag", tag);
        privateBundle.putInt("hashCode", tag.hashCode());
        privateBundle.putSerializable("type", builderType);
        return privateBundle;
    }

    protected abstract void onProvideData(HitogoParamsHolder holder);

    public final void show(@NonNull String tag) {
        this.tag = tag;
        build().show();
    }

    public final void showDelayed(@NonNull String tag, long millis) {
        this.tag = tag;

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

    protected final HitogoController getController() {
        return containerRef.get().getController();
    }
}

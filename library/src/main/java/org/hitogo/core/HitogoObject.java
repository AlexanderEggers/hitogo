package org.hitogo.core;

import android.app.Activity;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

@SuppressWarnings({"WeakerAccess", "unused"})
public abstract class HitogoObject {
    protected HitogoController controller;
    protected boolean isVisible;
    private int hashCode;

    protected HitogoObject(@NonNull HitogoController controller, int hashCode) {
        this.controller = controller;
        this.hashCode = hashCode;
    }

    public final void show(@NonNull final Fragment fragment) {
        final HitogoObject object = controller.validate(this);

        if(object.hasAnimation()) {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if(fragment.isAdded()) {
                        showNewHitogo(fragment.getActivity(), object);
                    }
                }
            }, object.getAnimationDuration() + 100);
        } else {
            showNewHitogo(fragment.getActivity(), object);
        }
    }

    public final void show(@NonNull final Activity activity) {
        final HitogoObject object = controller.validate(this);

        if(object.hasAnimation()) {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if(!activity.isFinishing()) {
                        showNewHitogo(activity, object);
                    }
                }
            }, object.getAnimationDuration() + 100);
        } else {
            showNewHitogo(activity, object);
        }
    }

    public final void hide() {
        controller.hideHitogo();
    }

    private void showNewHitogo(Activity activity, HitogoObject object) {
        if(!object.isVisible()) {
            object.makeVisible(activity);
        }
    }

    public final boolean isVisible() {
        return isVisible;
    }

    public final void setVisibility(boolean isVisible) {
        this.isVisible = isVisible;
    }

    @Override
    public final int hashCode() {
        return hashCode;
    }

    @Override
    public final boolean equals(@NonNull Object obj) {
        return obj instanceof HitogoObject && this.hashCode == obj.hashCode();
    }

    public boolean hasAnimation() {
        return false;
    }

    public long getAnimationDuration() {
        return 0;
    }

    @Nullable
    public Integer getLayoutViewId() {
        return null;
    }

    abstract void makeVisible(@NonNull Activity activity);
    abstract void makeInvisible();
}

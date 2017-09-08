package org.hitogo.core;

import android.app.Activity;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;

@SuppressWarnings({"WeakerAccess", "unused"})
public abstract class HitogoObject<T extends HitogoParams> {
    private static final int DEFAULT_ANIMATION_LENGTH = 0;

    private static final int CURRENT_CROUTON = 0;
    private static final int LAST_CROUTON = 1;

    private HitogoController controller;
    private int hashCode;
    private boolean attached;
    private boolean detached;
    private boolean hasAnimation;

    public final HitogoObject<T> startHitogo(@NonNull T params) throws IllegalAccessException {
        if(attached) {
            throw new IllegalAccessException("Cannot apply parameter to a visible Hitogo.");
        }

        this.controller = params.getController();
        this.hashCode = params.getHashCode();
        this.hasAnimation = params.hasAnimation();
        onCreate(params);
        onCreate(params, params.getController());
        return this;
    }

    public final void show(final Activity activity) {
        internalShow(activity, null);
    }

    public final void show(final Fragment fragment) {
        internalShow(fragment.getActivity(), fragment);
    }

    private void internalShow(final Activity activity, final Fragment fragment) {
        final HitogoObject[] objects = controller.validate(this);
        final HitogoObject current = objects[CURRENT_CROUTON];
        final HitogoObject last = objects[LAST_CROUTON];

        if(last != null && last.hasAnimation() && last.isClosing()) {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if((fragment != null && fragment.isAdded()) || !activity.isFinishing()) {
                        makeVisible(activity, current);
                    }
                }
            }, current.getAnimationDuration() + 100);
        } else {
            makeVisible(activity, current);
        }
    }

    private void makeVisible(Activity activity, HitogoObject object) {
        if(!object.isAttached()) {
            object.onAttach(activity);

            if(hasAnimation) {
                onShowAnimation(activity);
            } else {
                onShowDefault(activity);
            }
        }
    }

    protected final void makeInvisible() {
        if(isAttached()) {
            if(hasAnimation) {
                onDetachAnimation();
            } else {
                onDetachDefault();
                onGone();
            }
        }
    }

    protected void onCreate(@NonNull T params) {

    }

    protected void onCreate(@NonNull T params, @NonNull HitogoController controller) {

    }

    protected void onAttach(@NonNull Activity activity) {
        attached = true;
        detached = false;
    }

    protected void onShowDefault(Activity activity) {

    }

    protected void onShowAnimation(Activity activity) {

    }

    protected void onDetachDefault() {
        attached = false;

    }

    protected void onDetachAnimation() {
        attached = false;
    }

    protected final void onGone() {
        detached = true;
    }

    public long getAnimationDuration() {
        Log.d(HitogoObject.class.getName(), "Animation handling incorrect. Override getAnimationDuration() in your class.");
        return DEFAULT_ANIMATION_LENGTH;
    }

    protected final boolean isDetached() {
        return detached;
    }

    protected final boolean isAttached() {
        return attached;
    }

    protected final boolean isClosing() {
        return !attached && !detached;
    }

    public final boolean hasAnimation() {
        return hasAnimation;
    }

    public final void close() {
        controller.closeHitogo();
    }

    @Override
    public final int hashCode() {
        return hashCode;
    }

    @Override
    public final boolean equals(@NonNull Object obj) {
        return obj instanceof HitogoObject && this.hashCode == obj.hashCode();
    }
}

package org.hitogo.core;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;

import java.lang.ref.WeakReference;

@SuppressWarnings({"WeakerAccess", "unused"})
public abstract class HitogoObject<T extends HitogoParams> extends HitogoLifecycleCallback<T> {

    private static final int NO_ANIMATION_LENGTH = 0;

    private static final int CURRENT_CROUTON = 0;
    private static final int LAST_CROUTON = 1;

    public static final int TYPE_VIEW = 0;
    public static final int TYPE_DIALOG = 1;

    private boolean attached;
    private boolean detached;

    private WeakReference<Context> contextRef;
    private HitogoController controller;
    private int hashCode;
    private int type;
    private boolean hasAnimation;
    private View view;
    private View rootView;
    private Dialog dialog;

    public final HitogoObject<T> startHitogo(@NonNull T params) throws IllegalAccessException {
        if(attached) {
            throw new IllegalAccessException("Cannot apply parameter to a visible Hitogo.");
        }

        this.contextRef = params.getContextRef();
        this.controller = params.getController();
        this.hashCode = params.getHashCode();
        this.hasAnimation = params.hasAnimation();
        this.type = params.getType();

        onCheckStart(getContext(), params);
        onCreate(params);
        onCreate(params, controller);

        if(type == TYPE_VIEW) {
            LayoutInflater inflater = (LayoutInflater) getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = onCreateView(getContext(), inflater, params);
        } else {
            dialog = onCreateDialog(getContext(), params);
        }

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
            attached = true;
            detached = false;

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
                onDetachAnimation(getContext());

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        onGone();
                        detached = true;
                    }
                }, getAnimationDuration());
            } else {
                onDetachDefault(getContext());
                onGone();
                detached = true;
            }
        }
    }

    public final void close() {
        controller.closeHitogo();
    }

    public long getAnimationDuration() {
        return NO_ANIMATION_LENGTH;
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

    @NonNull
    public final Context getContext() {
        return contextRef.get();
    }

    @Nullable
    public final View getRootView() {
        return rootView;
    }

    @NonNull
    public final HitogoController getController() {
        return controller;
    }

    public final int getType() {
        return type;
    }

    @Nullable
    protected final View getView() {
        return view;
    }

    @Nullable
    protected final Dialog getDialog() {
        return dialog;
    }

    @Override
    public final int hashCode() {
        return hashCode;
    }

    @Override
    public final boolean equals(@NonNull Object obj) {
        return obj instanceof HitogoObject && this.hashCode == obj.hashCode() &&
                this.type == ((HitogoObject) obj).getType();
    }
}

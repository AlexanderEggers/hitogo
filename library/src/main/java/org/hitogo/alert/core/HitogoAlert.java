package org.hitogo.alert.core;

import android.app.Activity;
import android.app.Dialog;
import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import org.hitogo.core.HitogoContainer;
import org.hitogo.core.HitogoController;

import java.lang.ref.WeakReference;

@SuppressWarnings({"WeakerAccess", "unused"})
public abstract class HitogoAlert<T extends HitogoAlertParams> extends HitogoAlertLifecycle<T> {

    public static final int DEFAULT_SHOW_DELAY_IN_MS = 1000;
    public static final int NO_ANIMATION_LENGTH = 0;

    private static final int CURRENT_CROUTON = 0;
    private static final int LAST_CROUTON = 1;

    private boolean attached;
    private boolean detached;
    private boolean hasAnimation;
    private boolean closeOthers;

    private String tag;
    private int hashCode;
    private HitogoAlertType type;

    private HitogoVisibilityListener listener;

    private WeakReference<HitogoContainer> containerRef;
    private View view;
    private Dialog dialog;

    final HitogoAlert<T> startHitogo(@NonNull HitogoContainer container, @NonNull T params)
            throws IllegalAccessException {

        if(attached) {
            throw new IllegalAccessException("Cannot apply parameter to a visible Hitogo.");
        }

        this.containerRef = new WeakReference<>(container);
        this.hashCode = params.getHashCode();
        this.closeOthers = params.isClosingOthers();
        this.hasAnimation = params.hasAnimation();
        this.type = params.getType();
        this.tag = params.getTag();
        this.listener = params.getVisibilityListener();

        onCheck(params);
        onCheck(getController(), params);

        if(listener != null) {
            listener.onCreate(this);
        }

        onCreate(params);
        onCreate(getController(), params);

        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(type == HitogoAlertType.VIEW) {
            view = onCreateView(inflater, getActivity(), params);
        } else {
            dialog = onCreateDialog(inflater, getActivity(), params);
        }

        return this;
    }

    public final void show() {
        internalShow(false);
    }

    public final void forceShow() {
        internalShow(true);
    }

    private void internalShow(final boolean force) {
        final HitogoAlert[] objects = getController().validate(this);
        final HitogoAlert current = objects[CURRENT_CROUTON];
        final HitogoAlert last = objects[LAST_CROUTON];

        if(last != null && last.hasAnimation() && last.isClosing()) {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if(getContainer().getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.CREATED)) {
                        makeVisible(current, force);
                    }
                }
            }, current.getAnimationDuration() + 100);
        } else {
            makeVisible(current, force);
        }
    }

    public final void showDelayed(long millis) {
        internalShowDelayed(millis, false);
    }

    public final void forceShowDelayed(long millis) {
        internalShowDelayed(millis, true);
    }

    private void internalShowDelayed(long millis, final boolean force) {
        long delayInMs = millis;

        if (millis <= 0) {
            Log.i(HitogoAlertBuilder.class.getName(), "Delayed cannot be null. Using default delay time value.");
            delayInMs = DEFAULT_SHOW_DELAY_IN_MS;
        }

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(getContainer().getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.CREATED)) {
                    internalShow(force);
                }
            }
        }, delayInMs);
    }

    public final void makeVisible(HitogoAlert object, boolean force) {
        if(!object.isAttached()) {
            object.onAttach(getContainer().getActivity());
            attached = true;
            detached = false;

            if(listener != null) {
                listener.onShow(this);
            }

            if(hasAnimation && !force) {
                onShowAnimation(getContainer().getActivity());
            } else {
                onShowDefault(getContainer().getActivity());
            }
        }
    }

    public final void makeInvisible(boolean force) {
        if(isAttached()) {
            onDetach(getActivity());
            attached = false;

            if(listener != null) {
                listener.onClose(this);
            }

            if(hasAnimation && !force) {
                onCloseAnimation(getActivity());

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        detached = true;
                    }
                }, getAnimationDuration());
            } else {
                onCloseDefault(getActivity());
                detached = true;
            }
        }
    }

    public final void close() {
        getController().closeByTag(tag);
    }

    public final void forceClose() {
        getController().forceCloseByTag(tag);
    }

    public long getAnimationDuration() {
        return NO_ANIMATION_LENGTH;
    }

    public final boolean isDetached() {
        return detached;
    }

    public final boolean isAttached() {
        return attached;
    }

    public final boolean isClosing() {
        return !attached && !detached;
    }

    public final boolean hasAnimation() {
        return hasAnimation && getAnimationDuration() > NO_ANIMATION_LENGTH;
    }

    public final boolean isClosingOthers() {
        return closeOthers;
    }

    @NonNull
    public final Activity getActivity() {
        return containerRef.get().getActivity();
    }

    @Nullable
    public final View getRootView() {
        return containerRef.get().getView();
    }

    @NonNull
    public final HitogoContainer getContainer() {
        return containerRef.get();
    }

    @NonNull
    public final HitogoController getController() {
        return containerRef.get().getController();
    }

    @NonNull
    public final HitogoAlertType getType() {
        return type;
    }

    @Nullable
    public final View getView() {
        return view;
    }

    @Nullable
    public final Dialog getDialog() {
        return dialog;
    }

    public final String getTag() {
        return tag;
    }

    @Override
    public final int hashCode() {
        return hashCode;
    }

    @Override
    public final boolean equals(Object obj) {
        return obj != null && obj instanceof HitogoAlert && this.hashCode == obj.hashCode();
    }
}

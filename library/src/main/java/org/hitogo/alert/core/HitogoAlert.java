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

/**
 * This class is used to abstract the lifecycle of an alert. The class is extending the
 * HitogoAlertLifecycle to achieve that. Each instance of this class can have a different parameter
 * holder which provides values from the builder system.
 *
 * @param <T> Generics for the params class which is used to provide builder values to the object.
 * @see HitogoAlertLifecycle
 * @see HitogoAlertParams
 * @since 1.0.0
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public abstract class HitogoAlert<T extends HitogoAlertParams> extends HitogoAlertLifecycle<T> {

    public static final int DEFAULT_SHOW_DELAY_IN_MS = 1000;
    public static final int NO_ANIMATION_LENGTH = 0;
    public static final int ANIMATION_BREAK_IN_MS = 100;

    private static final int CURRENT_HITOGO = 0;
    private static final int LAST_HITOGO = 1;

    private boolean attached;
    private boolean detached;
    private boolean hasAnimation;
    private boolean closeOthers;

    private String tag;
    private int hashCode;
    private HitogoAlertType type;

    private HitogoVisibilityListener listener;

    private WeakReference<HitogoContainer> containerRef;
    private T params;
    private View view;
    private Dialog dialog;

    /**
     * Creates the alert and starts the internal lifecycle process. This method is only used by
     * the builder system internally.
     *
     * @param container Reference to the HitogoContainer which has been used to
     *                  create this object.
     * @param params    Reference to parameter holder class which is providing the builder values.
     * @return Created alert object which can be used to be displayed.
     * @see org.hitogo.core.HitogoContainer
     * @see org.hitogo.alert.view.HitogoViewParams
     * @since 1.0.0
     */
    final HitogoAlert<T> create(@NonNull HitogoContainer container, @NonNull T params) {
        this.containerRef = new WeakReference<>(container);
        this.params = params;
        this.hashCode = params.getHashCode();
        this.closeOthers = params.isClosingOthers();
        this.hasAnimation = params.hasAnimation();
        this.type = params.getType();
        this.tag = params.getTag();
        this.listener = params.getVisibilityListener();

        onCheck(params);
        onCheck(getController(), params);

        if (listener != null) {
            listener.onCreate(this);
        }

        onCreate(params);
        onCreate(getController(), params);

        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(inflater == null) {
            throw new IllegalStateException("Fatal error: Layout inflater is null.");
        }

        if (type == HitogoAlertType.VIEW) {
            view = onCreateView(inflater, getActivity(), params);
        } else {
            dialog = onCreateDialog(inflater, getActivity(), params);
        }

        return this;
    }

    /**
     * Attaches this alert to the user interface. This method won't force the display process
     * and will wait for running animations of other alerts.
     *
     * @since 1.0.0
     */
    public final void show() {
        internalShow(false);
    }

    /**
     * Forces the attach-process for this alert. This means that any running animations will be
     * ignored. <b>Keep in mind that forcing this process could reduce performance and/or the
     * visual quality.</b>
     *
     * @since 1.0.0
     */
    public final void forceShow() {
        internalShow(true);
    }

    /**
     * Internal process which is managing the process to attach an alert to the display. This
     * process is depending on the given parameter which will decide if the method should wait for
     * other running animations.
     *
     * @param force determines if the attach-process should be forced (therefore not waiting for
     *              running animations of other alerts) or not
     * @since 1.0.0
     */
    private void internalShow(final boolean force) {
        final HitogoAlert[] objects = getController().validate(this);
        final HitogoAlert current = objects[CURRENT_HITOGO];
        final HitogoAlert last = objects[LAST_HITOGO];

        if (last != null && last.hasAnimation() && last.isClosing()) {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (getContainer().getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.CREATED)) {
                        makeVisible(current, force);
                    }
                }
            }, current.getAnimationDuration() + ANIMATION_BREAK_IN_MS);
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

        if (millis <= NO_ANIMATION_LENGTH) {
            Log.i(HitogoAlertBuilder.class.getName(), "Delayed cannot be null. Using default delay time value.");
            delayInMs = DEFAULT_SHOW_DELAY_IN_MS;
        }

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (getContainer().getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.CREATED)) {
                    internalShow(force);
                }
            }
        }, delayInMs);
    }

    public final void makeVisible(HitogoAlert object, boolean force) {
        if (!object.isAttached()) {
            object.onAttach(getContainer().getActivity());
            attached = true;
            detached = false;

            if (listener != null) {
                listener.onShow(this);
            }

            if (hasAnimation && !force) {
                onShowAnimation(getContainer().getActivity());
            } else {
                onShowDefault(getContainer().getActivity());
            }
        }
    }

    public final void makeInvisible(boolean force) {
        if (isAttached()) {
            onDetach(getActivity());
            attached = false;

            if (listener != null) {
                listener.onClose(this);
            }

            if (hasAnimation && !force) {
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

    @NonNull
    public T getParams() {
        return params;
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

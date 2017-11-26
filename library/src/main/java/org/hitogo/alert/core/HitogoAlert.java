package org.hitogo.alert.core;

import android.app.Dialog;
import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import org.hitogo.BuildConfig;
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

    /**
     * This value is as an default value for the (display-)delay if the alerts is shown using the
     * onCreate of one control. To prevent visual bugs while showing this alert (due to not
     * finished layout rendering in this state), this value is used to force a delay.
     */
    public static final int DEFAULT_SHOW_DELAY_IN_MS = 1000;

    /**
     * This value is used if no animation is defined.
     */
    public static final int NO_ANIMATION_LENGTH = 0;

    /**
     * This value is used for the break between the hide-animation and showing the new alert.
     */
    public static final int ANIMATION_BREAK_IN_MS = 100;

    /**
     * This value describes the minimum delay value which can be used.
     */
    public static final int MIN_ANIMATION_LENGTH = 100;

    /**
     * Defines the current (new) alert inside the given controller array.
     */
    private static final int CURRENT_ALERT = 0;

    /**
     * Defines the last (current) alert inside the given controller array.
     */
    private static final int LAST_ALERT = 1;

    /**
     * True if this alert has been attached to the controller, otherwise false.
     */
    private boolean attached;

    /**
     * True if this alert has been detached from the controller, otherwise false.
     */
    private boolean detached;

    /**
     * Defines if this alert has an animation which will be shown if the alert is changing it's
     * visibility.
     */
    private boolean hasAnimation;

    /**
     * True if this alert should hide all other alert from it's type, false otherwise.
     */
    private boolean closeOthers;

    /**
     * This value is used to define the tag this alert. The tag can be used to close a specific via
     * the controller if needed.
     */
    private String tag;

    /**
     * The hashcode is the value which is used by the controller to compare two different alerts.
     */
    private int hashCode;

    /**
     * Defines the type of this alert. This type is used to determine specific lifecycle methods or
     * to limit certain features to ensure ux-errors.
     */
    private HitogoAlertType type;

    /**
     * This value represents the visibility listener that can be used to react to specific alert
     * states.
     */
    private HitogoVisibilityListener listener;

    private WeakReference<HitogoContainer> containerRef;

    /**
     * Parameter object which holds all relevant data for this specific alert.
     */
    private T params;

    /**
     * View object which defines this alert. This view is null if the type is 'dialog'.
     */
    private View view;

    /**
     * Dialog object which defines this alert. This view is null if the type is 'view'.
     */
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
    final HitogoAlert<T> create(final @NonNull HitogoContainer container, final @NonNull T params) {
        this.containerRef = new WeakReference<>(container);
        this.params = params;
        this.hashCode = params.getHashCode();
        this.closeOthers = params.isClosingOthers();
        this.hasAnimation = params.hasAnimation();
        this.type = params.getType();
        this.tag = params.getTag();
        this.listener = params.getVisibilityListener();

        if (BuildConfig.DEBUG || getController().shouldOverrideDebugMode()) {
            onCheck(params);
            onCheck(getController(), params);
        }

        if (listener != null) {
            listener.onCreate(this);
        }

        onCreate(params);
        onCreate(getController(), params);

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (type == HitogoAlertType.VIEW) {
            view = onCreateView(inflater, getContext(), params);
        } else {
            dialog = onCreateDialog(inflater, getContext(), params);
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
        show(false);
    }

    /**
     * Method which is managing the process to attach an alert to the display. This
     * process is depending on the given parameter which will decide if the method should wait for
     * other running animations.
     *
     * @param force determines if the attach-process should be forced (therefore not waiting for
     *              running animations of other alerts and will not play the own animation) or not.
     *              <b>Keep in mind that forcing this process could reduce performance and/or the
     *              visual quality.</b>
     * @since 1.0.0
     */
    public final void show(final boolean force) {
        final HitogoAlert[] objects = getController().validate(this);
        final HitogoAlert current = objects[CURRENT_ALERT];
        final HitogoAlert last = objects[LAST_ALERT];

        if (last != null && last.hasAnimation() && last.isClosing() && !force) {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (getContainer().getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.CREATED)) {
                        makeVisible(current, false);
                    }
                }
            }, current.getAnimationDuration() + ANIMATION_BREAK_IN_MS);
        } else {
            makeVisible(current, force);
        }
    }

    public final void showDelayed(final long millis) {
        if (getType().equals(HitogoAlertType.VIEW)) {
            internalShowDelayed(millis, false);
        } else {
            show(false);
        }
    }

    public final void showDelayed(final long millis, final boolean force) {
        if (getType().equals(HitogoAlertType.VIEW)) {
            internalShowDelayed(millis, force);
        } else {
            show(force);
        }
    }

    private void internalShowDelayed(final long millis, final boolean force) {
        long delayInMs = millis;

        if (millis <= MIN_ANIMATION_LENGTH) {
            Log.i(HitogoAlertBuilder.class.getName(), "Delayed time is too short. Using " +
                    "default delay time value.");
            delayInMs = MIN_ANIMATION_LENGTH;
        }

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (getContainer().getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.CREATED)) {
                    show(force);
                }
            }
        }, delayInMs);
    }

    public final void makeVisible(final HitogoAlert object, final boolean force) {
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

    public final void makeInvisible(final boolean force) {
        if (isAttached()) {
            onDetach(getContext());
            attached = false;

            if (listener != null) {
                listener.onClose(this);
            }

            if (hasAnimation && !force) {
                onCloseAnimation(getContext());

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        detached = true;
                    }
                }, getAnimationDuration());
            } else {
                onCloseDefault(getContext());
                detached = true;
            }
        }
    }

    public final void close() {
        getController().closeByTag(tag);
    }

    public final void close(final boolean force) {
        getController().closeByTag(tag, force);
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
    public final Context getContext() {
        return containerRef.get().getActivity();
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
    public final T getParams() {
        return params;
    }

    @NonNull
    public final String getTag() {
        return tag;
    }

    @Nullable
    public final View getRootView() {
        return containerRef.get().getView();
    }

    @Nullable
    public final View getView() {
        return view;
    }

    @Nullable
    public final Dialog getDialog() {
        return dialog;
    }

    @Override
    public final int hashCode() {
        return hashCode;
    }

    @Override
    public final boolean equals(final Object obj) {
        return obj != null && obj instanceof HitogoAlert && this.hashCode == obj.hashCode();
    }
}

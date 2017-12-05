package org.hitogo.alert.core;

import android.app.Dialog;
import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;

import org.hitogo.BuildConfig;
import org.hitogo.alert.view.ViewAlertParams;
import org.hitogo.core.HitogoContainer;
import org.hitogo.core.HitogoController;

import java.lang.ref.WeakReference;

/**
 * This class is used to abstract the lifecycle of an alert. The class is extending the
 * AlertLifecycle to achieve that. Each instance of this class can have a different parameter
 * holder which provides values from the builder system.
 *
 * @param <T> Generics for the params class which is used to provide builder values to the object.
 * @see AlertLifecycle
 * @see AlertParams
 * @since 1.0.0
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public abstract class AlertImpl<T extends AlertParams> extends AlertLifecycle<T> implements Alert<T> {

    /**
     * This value is as an default value for the (display-)delay if the alerts is shown using the
     * onCreate of one control. To prevent visual bugs while showing this alert (due to not
     * finished layout rendering in this state), this value is used to force a delay.
     */
    public static final int DEFAULT_SHOW_DELAY_IN_MS = 1000;

    /**
     * This value is used if no animation is defined for this alert.
     */
    public static final int NO_ANIMATION_LENGTH = 0;

    /**
     * This value describes the minimum animation length.
     */
    public static final int MIN_ANIMATION_LENGTH = 100;

    private boolean attached;
    private boolean detached;
    private boolean hasAnimation;
    private boolean closeOthers;

    private String tag;

    private int hashCode;

    private AlertType type;
    private VisibilityListener listener;
    private WeakReference<HitogoContainer> containerRef;
    private T params;

    private View view;
    private Dialog dialog;
    private PopupWindow popup;

    /**
     * Creates the alert and starts the internal lifecycle process. This method is only used by
     * the builder system internally.
     *
     * @param container Reference to the HitogoContainer which has been used to
     *                  create this object.
     * @param params    Reference to parameter holder class which is providing the builder values.
     * @return Created alert object which can be used to be displayed.
     * @see HitogoContainer
     * @see ViewAlertParams
     * @since 1.0.0
     */
    final AlertImpl<T> create(final @NonNull HitogoContainer container, final @NonNull T params) {
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
        switch (type) {
            case VIEW:
                view = onCreateView(inflater, getContext(), params);
                break;
            case DIALOG:
                dialog = onCreateDialog(inflater, getContext(), params);
                break;
            case POPUP:
                popup = onCreatePopup(inflater, getContext(), params);
                break;
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
        getRootView().post(new Runnable() {
            @Override
            public void run() {
                getController().show(AlertImpl.this, force);
            }
        });
    }

    public final void showLater(final boolean showLater) {
        getRootView().post(new Runnable() {
            @Override
            public void run() {
                getController().show(AlertImpl.this, false, showLater);
            }
        });
    }

    public final void showDelayed(final long millis) {
        showDelayed(millis, false);
    }

    public final void showDelayed(final long millis, final boolean force) {
        getRootView().post(new Runnable() {
            @Override
            public void run() {
                if (hasAnimation()) {
                    internalShowDelayed(millis, force);
                } else {
                    show(force);
                }
            }
        });
    }

    private void internalShowDelayed(final long millis, final boolean force) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (getContainer().getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.CREATED)) {
                    show(force);
                }
            }
        }, millis);
    }

    public final void makeVisible(final boolean force) {
        if (!isAttached()) {
            onAttach(getContainer().getActivity());
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

            if (hasAnimation() && !force) {
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
    public final AlertType getType() {
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

    @Nullable
    public PopupWindow getPopup() {
        return popup;
    }

    @Override
    public final int hashCode() {
        return hashCode;
    }

    @Override
    public final boolean equals(final Object obj) {
        return obj != null && obj instanceof AlertImpl && this.hashCode == obj.hashCode();
    }
}

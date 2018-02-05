package org.hitogo.alert.core;

import android.app.Dialog;
import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;

import org.hitogo.alert.view.ViewAlertParams;
import org.hitogo.core.HitogoContainer;
import org.hitogo.core.HitogoController;
import org.hitogo.core.HitogoUtils;

import java.lang.ref.WeakReference;
import java.util.List;

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
@SuppressWarnings({"WeakerAccess", "unused", "unchecked"})
public abstract class AlertImpl<T extends AlertParams> extends AlertLifecycle<T> implements Alert<T> {

    private static final int NO_ANIMATION_LENGTH = 0;

    private boolean attached;
    private boolean detached;
    private boolean hasAnimation;
    private boolean closeOthers;

    private String tag;

    private int hashCode;
    private int state;

    private AlertType type;
    private List<VisibilityListener> visibilityListeners;
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
    protected AlertImpl<T> create(final @NonNull HitogoContainer container, final @NonNull T params) {
        this.containerRef = new WeakReference<>(container);
        this.params = params;
        this.hashCode = HitogoUtils.generateAlertHashCode(params);
        this.closeOthers = params.isClosingOthers();
        this.hasAnimation = params.hasAnimation();
        this.type = params.getType();
        this.tag = params.getTag();
        this.visibilityListeners = params.getVisibilityListener();
        this.state = params.getState() != null ? params.getState() : -1;

        if (getController().provideIsDebugState()) {
            onCheck(params);
        }

        for (VisibilityListener listener : visibilityListeners) {
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

    @Override
    public void show() {
        show(false);
    }

    /**
     * Displays this alert object on the user screen if the alert is not visible yet.
     *
     * @param force Determines if the animation for the show-process should displayed or not.
     * @since 1.0.0
     */
    public void show(final boolean force) {
        getRootView().post(new Runnable() {
            @Override
            public void run() {
                getController().show(AlertImpl.this, force);
            }
        });
    }

    /**
     * Prepares the show-process for this alert. Depending on the input, the alert will be made
     * visible or stay invisible for later.
     *
     * @param showLater Determines if the alert should be displayed later.
     * @since 1.0.0
     */
    public void showLater(final boolean showLater) {
        getRootView().post(new Runnable() {
            @Override
            public void run() {
                getController().show(AlertImpl.this, false, showLater);
            }
        });
    }

    /**
     * Delays the show-process for this alert. The delay is depending the input.
     *
     * @param millis Delay in milliseconds.
     * @since 1.0.0
     */
    public void showDelayed(final long millis) {
        showDelayed(millis, false);
    }

    /**
     * Delays the show-process for this alert. The delay is depending the input.
     *
     * @param millis Delay in milliseconds.
     * @param force  Determines if the animation for the show-process should displayed or not.
     * @since 1.0.0
     */
    public void showDelayed(final long millis, final boolean force) {
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

    /**
     * Starts the delay process to display this alert. <b>Internal use only.</b>
     *
     * @param millis Delay in milliseconds.
     * @param force  Determines if the animation for the show-process should displayed or not.
     * @since 1.0.0
     */
    protected void internalShowDelayed(final long millis, final boolean force) {
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

    /**
     * Starts the process to display this alert. <b>Internal use only.</b>
     *
     * @param force Determines if the animation for the show-process should displayed or not.
     * @since 1.0.0
     */
    public void makeVisible(final boolean force) {
        if (!isAttached()) {
            onAttach(getContainer().getActivity());
            attached = true;
            detached = false;

            for (VisibilityListener listener : visibilityListeners) {
                listener.onShow(this);
            }

            if (hasAnimation && !force) {
                onShowAnimation(getContainer().getActivity());
            } else {
                onShowDefault(getContainer().getActivity());
            }
        }
    }

    /**
     * Starts the process to hide this alert. <b>Internal use only.</b>
     *
     * @param force Determines if the animation for the hide-process should displayed or not.
     * @since 1.0.0
     */
    public void makeInvisible(final boolean force) {
        if (isAttached()) {
            onDetach(getContext());
            attached = false;

            for (VisibilityListener listener : visibilityListeners) {
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

    @Override
    public void close() {
        getController().closeByAlert(this);
    }

    /**
     * Closes this alert object on the user screen if it's still visible.
     *
     * @param force Determines if the animation for the hide-process should displayed or not.
     * @since 1.0.0
     */
    public void close(final boolean force) {
        getController().closeByAlert(this, force);
    }

    @Override
    public long getAnimationDuration() {
        return NO_ANIMATION_LENGTH;
    }

    @Override
    public boolean isDetached() {
        return detached;
    }

    @Override
    public boolean isAttached() {
        return attached;
    }

    @Override
    public boolean isClosing() {
        return !attached && !detached;
    }

    public boolean hasAnimation() {
        return hasAnimation && getAnimationDuration() > NO_ANIMATION_LENGTH;
    }

    /**
     * Returns if the alert is closing others (of the same alert type) if it's made visible.
     *
     * @return True if the alert is closing others, otherwise false.
     * @see AlertType
     * @since 1.0.0
     */
    public boolean isClosingOthers() {
        return closeOthers;
    }

    @Override
    public Context getContext() {
        return containerRef.get().getActivity();
    }

    public HitogoContainer getContainer() {
        return containerRef.get();
    }

    public HitogoController getController() {
        return containerRef.get().getController();
    }

    @Override
    public AlertType getType() {
        return type;
    }

    @Override
    public T getParams() {
        return params;
    }

    @Override
    public boolean hasPriority() {
        return getPriority() != null;
    }

    @Override
    public Integer getPriority() {
        return params.getPriority();
    }

    @Override
    public String getTag() {
        return tag;
    }

    /**
     * Returns the root view of the alert. This value is usually non-null if the alert is attached
     * to the view hierarchy (activity or fragment).
     *
     * @return Root view which has been used to attach the alert to the current visible layout.
     * Null if no root view was used.
     * @since 1.0.0
     */
    public View getRootView() {
        return containerRef.get().getView();
    }

    /**
     * Returns the view of the alert. This value is usually non-null if the alert is attached
     * to the view hierarchy (activity or fragment).
     *
     * @return View of the alert. Null if the alert is not using the view object.
     * @since 1.0.0
     */
    public View getView() {
        return view;
    }

    /**
     * Returns the dialog of the alert.
     *
     * @return Dialog of the alert. Null if the alert is not using the dialog object.
     * @since 1.0.0
     */
    public Dialog getDialog() {
        return dialog;
    }

    /**
     * Returns the popup of the alert.
     *
     * @return Popup of the alert. Null if the alert is not using the popup object.
     * @since 1.0.0
     */
    public PopupWindow getPopup() {
        return popup;
    }

    /**
     * Returns the alert hashcode.
     *
     * @return Hashcode for this alert.
     * @since 1.0.0
     */
    @Override
    public int hashCode() {
        return hashCode;
    }

    @Override
    public int getState() {
        return state;
    }

    @Override
    public boolean equals(final Object obj) {
        return obj != null && obj instanceof Alert && hashCode() == obj.hashCode();
    }
}

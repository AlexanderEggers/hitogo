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
    AlertImpl<T> create(final @NonNull HitogoContainer container, final @NonNull T params) {
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
            onCheck(getController(), params);
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

    /**
     * Attaches this alert to the user interface. This method won't force the display process
     * and will wait for running animations of other alerts.
     *
     * @since 1.0.0
     */
    public void show() {
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
    public void show(final boolean force) {
        getRootView().post(new Runnable() {
            @Override
            public void run() {
                getController().show(AlertImpl.this, force);
            }
        });
    }

    public void showLater(final boolean showLater) {
        getRootView().post(new Runnable() {
            @Override
            public void run() {
                getController().show(AlertImpl.this, false, showLater);
            }
        });
    }

    public void showDelayed(final long millis) {
        showDelayed(millis, false);
    }

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

    public void close() {
        getController().closeByAlert(this);
    }

    public void close(final boolean force) {
        getController().closeByAlert(this, force);
    }

    public long getAnimationDuration() {
        return NO_ANIMATION_LENGTH;
    }

    public boolean isDetached() {
        return detached;
    }

    public boolean isAttached() {
        return attached;
    }

    public boolean isClosing() {
        return !attached && !detached;
    }

    public boolean hasAnimation() {
        return hasAnimation && getAnimationDuration() > NO_ANIMATION_LENGTH;
    }

    public boolean isClosingOthers() {
        return closeOthers;
    }

    public Context getContext() {
        return containerRef.get().getActivity();
    }

    public HitogoContainer getContainer() {
        return containerRef.get();
    }

    public HitogoController getController() {
        return containerRef.get().getController();
    }

    public AlertType getType() {
        return type;
    }

    public T getParams() {
        return params;
    }

    public String getTag() {
        return tag;
    }

    public View getRootView() {
        return containerRef.get().getView();
    }

    public View getView() {
        return view;
    }

    public Dialog getDialog() {
        return dialog;
    }

    public PopupWindow getPopup() {
        return popup;
    }

    @Override
    public int hashCode() {
        return hashCode;
    }

    public int getState() {
        return state;
    }

    @Override
    public boolean equals(final Object obj) {
        return obj != null && obj instanceof Alert && hashCode() == obj.hashCode();
    }
}

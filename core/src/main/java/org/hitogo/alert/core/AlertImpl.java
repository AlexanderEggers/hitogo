package org.hitogo.alert.core;

import android.app.Activity;
import android.app.Dialog;
import androidx.lifecycle.Lifecycle;
import android.content.Context;
import android.os.Handler;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;

import org.hitogo.alert.view.ViewAlertParams;
import org.hitogo.core.HitogoAccessor;
import org.hitogo.core.HitogoContainer;
import org.hitogo.core.HitogoController;
import org.hitogo.core.HitogoHelper;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * This class is used to abstract the lifecycle of an alert. The class is extending the
 * AlertLifecycle to achieve that. Each instance of this class can have a different parameter
 * object which provides values from the builder system.
 *
 * @param <T> Generics for the params class which is used to provide builder values to the object.
 * @see AlertLifecycle
 * @see AlertParams
 * @since 1.0.0
 */
@SuppressWarnings({"unchecked"})
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
    private HitogoHelper helper;
    private HitogoAccessor accessor;

    private View view;
    private Dialog dialog;
    private PopupWindow popup;
    private Object other;

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
        this.helper = getController().provideHelper();
        this.accessor = getController().provideAccessor();
        this.hashCode = helper.generateAlertHashCode(params);
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
            case OTHER:
                other = onCreateOther(inflater, getContext(), params);
        }
        return this;
    }

    /**
     * Displays this alert object on the user screen if the alert is not visible yet.
     *
     * @since 1.0.0
     */
    @Override
    public void show() {
        getRootView().post(new Runnable() {
            @Override
            public void run() {
                getController().show(AlertImpl.this);
            }
        });
    }

    /**
     * Prepares the show-process for this alert. The alert will stay invisible for later.
     *
     * @since 1.0.0
     */
    public void showLater() {
        showLater(true);
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
                getController().show(AlertImpl.this, showLater);
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
        getRootView().post(new Runnable() {
            @Override
            public void run() {
                internalShowDelayed(millis);
            }
        });
    }

    /**
     * Starts the delay process to display this alert. <b>Internal use only.</b>
     *
     * @param millis Delay in milliseconds.
     * @since 1.0.0
     */
    private void internalShowDelayed(final long millis) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (getContainer().getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.CREATED)) {
                    show();
                }
            }
        }, millis);
    }

    /**
     * Starts the process to display this alert. <b>Internal use only.</b>
     *
     * @since 1.0.0
     */
    public void makeVisible() {
        if (!isAttached()) {
            onAttach(getContainer().getActivity());
            attached = true;
            detached = false;

            for (VisibilityListener listener : visibilityListeners) {
                listener.onShow(this);
            }

            if (hasAnimation) {
                onShowAnimation(getContainer().getActivity());
            } else {
                onShowDefault(getContainer().getActivity());
            }
        }
    }

    /**
     * Starts the process to hide this alert. <b>Internal use only.</b>
     *
     * @since 1.0.0
     */
    public void makeInvisible() {
        if (isAttached()) {
            onDetach(getContext());
            attached = false;

            for (VisibilityListener listener : visibilityListeners) {
                listener.onClose(this);
            }

            if (hasAnimation()) {
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

    /**
     * Closes this alert object on the user screen if it's still visible.
     *
     * @since 1.0.0
     */
    @Override
    public void close() {
        getController().closeByAlert(this);
    }

    /**
     * Return the animation duration length for the show-/hide-process. The value is usually zero,
     * if the alert has no HitogoAnimation attached. It is still possible that the alert body has a
     * default animation that has a certain duration.
     *
     * @return a long
     * @since 1.0.0
     */
    @Override
    public long getAnimationDuration() {
        return NO_ANIMATION_LENGTH;
    }

    /**
     * Determines if the alert is detached (invisible) from the user screen.
     *
     * @return True if the alert is detached, otherwise false.
     * @since 1.0.0
     */
    @Override
    public boolean isDetached() {
        return detached;
    }

    /**
     * Determines if the alert is attached (visible) at the user screen.
     *
     * @return True if the alert is attached, otherwise false.
     * @since 1.0.0
     */
    @Override
    public boolean isAttached() {
        return attached;
    }

    /**
     * Determines the moment when the alert is being detached from the user screen. That means that
     * isAttached equals false and isDetached is still false. This caused by the animation that is
     * execute between showing and hiding.
     *
     * @return True if the alert is in the process of being closed, otherwise false.
     * @since 1.0.0
     */
    @Override
    public boolean isClosing() {
        return !attached && !detached;
    }

    /**
     * Returns if the alert can execute animations. This is determined by the HitogoAnimation object
     * if it is attached to the alert.
     *
     * @return True if the alert can execute animations, false otherwise.
     * @see org.hitogo.core.HitogoAnimation
     * @since 1.0.0
     */
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

    /**
     * Returns the alert context.
     *
     * @return a Context
     * @since 1.0.0
     */
    @Override
    public Context getContext() {
        return containerRef.get().getActivity();
    }

    /**
     * Returns the used HitogoContainer object for the alert.
     *
     * @return HitogoContainer of the alert.
     * @see HitogoContainer
     * @since 1.0.0
     */
    @NonNull
    public HitogoContainer getContainer() {
        return containerRef.get();
    }

    /**
     * Returns the used HitogoController object for the alert.
     *
     * @return HitogoController of the alert.
     * @see HitogoController
     * @since 1.0.0
     */
    @NonNull
    public HitogoController getController() {
        return containerRef.get().getController();
    }

    /**
     * Returns the used HitogoHelper object for the alert.
     *
     * @return a HitogoHelper object
     * @see HitogoHelper
     * @since 1.0.0
     */
    @NonNull
    public HitogoHelper getHelper() {
        return helper;
    }

    /**
     * Returns the used HitogoAccessor object for the alert.
     *
     * @return a HitogoAccessor object
     * @see HitogoAccessor
     * @since 1.0.0
     */
    @NonNull
    public HitogoAccessor getAccessor() {
        return accessor;
    }

    /**
     * Returns the alert type. The types are based on the enum AlertType which includes VIEW,
     * DIALOG, POPUP and OTHER.
     *
     * @return Type for this alert
     * @see AlertType
     * @since 1.0.0
     */
    @Override
    public AlertType getAlertType() {
        return type;
    }

    /**
     * Returns the alert params object. The params object is storing all values for the alert.
     *
     * @return an object which is extending AlertParams
     * @since 1.0.0
     */
    @Override
    public T getParams() {
        return params;
    }

    /**
     * Determines if the alert has a priority or not.
     *
     * @return True if the alert has a priority, false otherwise.
     * @since 1.0.0
     */
    @Override
    public boolean hasPriority() {
        return getPriority() != null;
    }

    /**
     * Returns the current priority.
     *
     * @return an Integer object or null
     * @since 1.0.0
     */
    @Override
    public Integer getPriority() {
        return params.getPriority();
    }

    /**
     * Returns the alert tag.
     *
     * @return a String or null
     * @since 1.0.0
     */
    @Override
    public String getTag() {
        return tag;
    }

    /**
     * Returns the root view of the alert. This result can be identical to the method
     * getActivityRootView() if the used container is implemented by an Activity.
     *
     * @return a View object or null
     * @since 1.0.0
     */
    public View getRootView() {
        return containerRef.get().getView();
    }

    /**
     * Returns the activity root view of the alert.
     *
     * @return a View object or null
     * @since 1.0.0
     */
    public View getActivityRootView() {
        return containerRef.get().getActivityView();
    }

    /**
     * Determines if the alert has been execute by a HitogoContainer that is an instance of
     * Activity.
     *
     * @return a boolean
     * @since 1.0.0
     */
    @Override
    public boolean isExecutedByActivity() {
        return getContainer() instanceof Activity;
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
     * Returns the underlying object of the alert.
     *
     * @return Base object of the alert. Null if the alert is not from type OTHER (AlertType).
     * @since 1.0.0
     */
    public <O extends Object> O getOther() {
        return (O) other;
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

    /**
     * Returns the alert custom state.
     *
     * @return a positive Int or -1 if non is set
     * @since 1.0.0
     */
    @Override
    public int getState() {
        return state;
    }

    /**
     * Compares this alert to the given alert.
     *
     * @return True if the two compared alerts are equals, false otherwise
     * @since 1.0.0
     */
    @Override
    public boolean equals(final Object obj) {
        return obj != null && obj instanceof Alert && hashCode() == obj.hashCode();
    }
}

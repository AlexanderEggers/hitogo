package org.hitogo.alert.view;

import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.hitogo.alert.core.Alert;
import org.hitogo.alert.core.AlertBuilder;
import org.hitogo.button.core.Button;
import org.hitogo.core.HitogoAnimation;

/**
 * Public api interface for the ViewAlertBuilderImpl. This interface includes all methods that can
 * be used by this builder.
 *
 * @see Alert
 * @since 1.0.0
 */
public interface ViewAlertBuilder extends AlertBuilder<ViewAlertBuilder, ViewAlert> {

    /**
     * Specifies that the alert has animations using the default animation object that is defined
     * inside the HitogoController (provideDefaultAlertAnimation).
     *
     * @return Builder object which has called this method.
     * @see org.hitogo.core.HitogoController
     * @see HitogoAnimation
     * @since 1.0.0
     */
    @NonNull
    ViewAlertBuilder withAnimations();

    /**
     * Specifies if the alert has animations. If true, the method using the default animation object
     * that is defined inside the HitogoController (provideDefaultAlertAnimation).
     *
     * @param withAnimation a boolean
     * @return Builder object which has called this method.
     * @see org.hitogo.core.HitogoController
     * @see HitogoAnimation
     * @since 1.0.0
     */
    @NonNull
    ViewAlertBuilder withAnimations(boolean withAnimation);

    /**
     * Specifies that the alert has animations using the default animation object that is defined
     * inside the HitogoController (provideDefaultAlertAnimation). The given parameter
     * innerLayoutViewId is used to improve that animation. That view could be used to execute a
     * fade -in/-out for it's elements during the animation. If this value is not set, the
     * animation could also use the provideDefaultAlertAnimationLayoutViewId(AlertType) which is
     * inside the HitogoController.
     *
     * @param innerLayoutViewId view resource id that is relevant for the animation
     * @return Builder object which has called this method.
     * @see org.hitogo.core.HitogoController
     * @see HitogoAnimation
     * @since 1.0.0
     */
    @NonNull
    ViewAlertBuilder withAnimations(@IdRes @Nullable Integer innerLayoutViewId);

    /**
     * Specifies if the alert has animations using the given HitogoAnimation object.
     *
     * @param animation a HitogoAnimation object
     * @return Builder object which has called this method.
     * @see HitogoAnimation
     * @since 1.0.0
     */
    @NonNull
    ViewAlertBuilder withAnimations(@Nullable HitogoAnimation animation);

    /**
     * Specifies if the alert has animations using the given HitogoAnimation object. The given
     * parameter innerLayoutViewId is used to improve that animation. That view could be used to
     * execute a fade -in/-out for it's elements during the animation. If this value is not set, the
     * animation could also use the provideDefaultAlertAnimationLayoutViewId(AlertType) which is
     * inside the HitogoController.
     *
     * @param innerLayoutViewId view resource id that is relevant for the animation
     * @param animation         a HitogoAnimation object
     * @return Builder object which has called this method.
     * @see org.hitogo.core.HitogoController
     * @see HitogoAnimation
     * @since 1.0.0
     */
    @NonNull
    ViewAlertBuilder withAnimations(@Nullable HitogoAnimation animation,
                                    @IdRes @Nullable Integer innerLayoutViewId);

    /**
     * Marks the alert as dismissible/closeable by clicking outside of the popup.
     *
     * @return Builder object which has called this method.
     * @since 1.0.0
     */
    @NonNull
    ViewAlertBuilder asDismissible();

    /**
     * Marks the alert as dismissible/closeable by clicking the relevant button/icon. The method
     * is creating a default close button if the given parameter is true.
     *
     * @param isDismissible a boolean
     * @return Builder object which has called this method.
     * @since 1.0.0
     */
    @NonNull
    ViewAlertBuilder asDismissible(boolean isDismissible);

    /**
     * Marks the alert as dismissible/closeable by clicking the given  close button object that is
     * attached to a view element.
     *
     * @param closeButton Button object or null
     * @return Builder object which has called this method.
     * @since 1.0.0
     */
    @NonNull
    ViewAlertBuilder asDismissible(@Nullable Button closeButton);

    /**
     * Specifies that the alert should ignore any given container that it can be attached to.
     * Instead the alert will be attached to a new activity layer.
     *
     * @return Builder object which has called this method.
     * @since 1.0.0
     */
    @NonNull
    ViewAlertBuilder asIgnoreLayout();

    /**
     * Specifies that the alert should use the overlay container. The overlay container is usually
     * part of the parent activity or fragment and should be used as a generic container element.
     * The view id for this container is defined inside the HitogoController
     * (provideDefaultViewAlertOverlayContainerId).
     *
     * @return Builder object which has called this method.
     * @see org.hitogo.core.HitogoController
     * @since 1.0.0
     */
    @NonNull
    ViewAlertBuilder asOverlay();

    /**
     * Specifies that the alert should use the overlay container that is defined by the given
     * view id parameter. The overlay container is usually part of the parent activity or fragment
     * and should be used as a generic container element.
     *
     * @return Builder object which has called this method.
     * @since 1.0.0
     */
    @NonNull
    ViewAlertBuilder asOverlay(@IdRes @Nullable Integer overlayId);

    /**
     * Specifies that the alert should use the layout container. The layout container is usually
     * part of a fragment or activity. The view id for this container is defined inside the
     * HitogoController (provideDefaultViewAlertLayoutContainerId).
     *
     * @return Builder object which has called this method.
     * @see org.hitogo.core.HitogoController
     * @since 1.0.0
     */
    @NonNull
    ViewAlertBuilder asLayoutChild();

    /**
     * Specifies that the alert should use the layout container that is defined by the given
     * view id parameter. The layout container is usually part of a fragment or activity.
     *
     * @return Builder object which has called this method.
     * @since 1.0.0
     */
    @NonNull
    ViewAlertBuilder asLayoutChild(@IdRes @Nullable Integer containerId);

    /**
     * Specifies that the alert should close all other alerts of the same type when made visible.
     *
     * @param closeOthers a boolean
     * @return Builder object which has called this method.
     * @since 1.0.0
     */
    @NonNull
    ViewAlertBuilder closeOthers(boolean closeOthers);

    /**
     * Specifies that the alert should be closed if it's layout has been clicked.
     *
     * @param dismissByClick a boolean
     * @return Builder object which has called this method.
     * @since 1.0.0
     */
    @NonNull
    ViewAlertBuilder dismissByLayoutClick(boolean dismissByClick);

    /**
     * Displays this alert object on the user screen if the alert is not visible yet.
     *
     * @param force Determines if the animation for the show-process should displayed or not.
     * @since 1.0.0
     */
    void show(boolean force);

    /**
     * Prepares the show-process for this alert. The alert will stay invisible for later.
     *
     * @since 1.0.0
     */
    void showLater();

    /**
     * Prepares the show-process for this alert. Depending on the input, the alert will be made
     * visible or stay invisible for later.
     *
     * @param showLater Determines if the alert should be displayed later.
     * @since 1.0.0
     */
    void showLater(boolean showLater);

    /**
     * Delays the show-process for this alert. The delay is depending the input.
     *
     * @param millis Delay in milliseconds.
     * @since 1.0.0
     */
    void showDelayed(long millis);

    /**
     * Delays the show-process for this alert. The delay is depending the input.
     *
     * @param millis Delay in milliseconds.
     * @param force  Determines if the animation for the show-process should displayed or not.
     * @since 1.0.0
     */
    void showDelayed(long millis, boolean force);
}

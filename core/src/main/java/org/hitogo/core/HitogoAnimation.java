package org.hitogo.core;

import android.support.annotation.NonNull;
import android.view.View;

import org.hitogo.alert.core.AlertImpl;
import org.hitogo.alert.view.ViewAlertParams;

/**
 * Abstract class for any animations that alerts can use. This class has defined animations for
 * the show and hide. It also includes a getter for the animation duration that is important for
 * the HitogoController.
 *
 * @see HitogoController
 * @since 1.0.0
 */
public abstract class HitogoAnimation {

    /**
     * Creates and runs the animation for the alert show-process.
     *
     * @param params     Params object that can provide values to the animation.
     * @param hitogoView View that will be animated
     * @param alert      alert object which can be used to access certain values/states
     * @since 1.0.0
     */
    public abstract void showAnimation(@NonNull final ViewAlertParams params, @NonNull final View hitogoView,
                                       @NonNull final AlertImpl alert);

    /**
     * Creates and runs the animation for the alert hide-process.
     *
     * @param params     Params object that can provide values to the animation.
     * @param hitogoView View that will be animated
     * @param alert      alert object which can be used to access certain values/states
     * @since 1.0.0
     */
    public abstract void hideAnimation(@NonNull final ViewAlertParams params, @NonNull final View hitogoView,
                                       @NonNull final AlertImpl alert);

    /**
     * Returns the animation duration for this animations.
     *
     * @return an long value which specifies the duration for this animation
     * @since 1.0.0
     */
    public abstract long getAnimationDuration();
}

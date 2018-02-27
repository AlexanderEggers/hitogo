package org.hitogo.core;

import android.app.Activity;
import android.arch.lifecycle.LifecycleOwner;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

/**
 * A class that handles the ability to show alerts. The actual logic in displaying the alert is
 * located inside the HitogoController. Other getter-methods of this interface are used to provide
 * values to the alert system. This class is also able to handle lifecycle events due to it's
 * LifecycleOwner.
 *
 * @see HitogoController
 * @since 1.0.0
 */
public interface HitogoContainer extends LifecycleOwner {

    /**
     * Returns the stored HitogoController of this object instance.
     *
     * @return a HitogoController
     * @since 1.0.0
     */
    @NonNull
    HitogoController getController();

    /**
     * Returns the view for this object instance.
     *
     * @return a View object or null
     * @since 1.0.0
     */
    @Nullable
    View getView();

    /**
     * Returns the activity instance for this object instance.
     *
     * @return a Activity
     * @since 1.0.0
     */
    @NonNull
    Activity getActivity();

    /**
     * Returns the view for the activity instance for this object instance.
     *
     * @return a View object or null
     */
    @Nullable
    View getActivityView();
}

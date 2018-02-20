package org.hitogo.core;

import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;

/**
 * This class is used to provide all needed alert-related values which has been initialised via
 * the builder system. In comparision: The counterpart, HitogoParamsHolder, can save and
 * provide data. The communication between those two params objects is the provideData-method.
 *
 * @param <T> Class which has been used to save the builder values.
 * @see HitogoParamsHolder
 * @since 1.0.0
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public abstract class HitogoParams<T extends HitogoParamsHolder> {

    private HitogoController controller;

    /**
     * This is an internal method which is used to fill the params object with different values.
     * This method is using the HitogoParamsHolder to generate those values.
     *
     * @param holder an object which is extending HitogoParamsHolder
     * @see HitogoParamsHolder
     * @since 1.0.0
     */
    @CallSuper
    protected void provideData(T holder, HitogoController controller) {
        this.controller = controller;
    }

    /**
     * This method should be called by the provideData to provide a public method which sub-classes
     * can use to retrieve their data from the HitogoParamsHolder.
     *
     * @param holder an object which is extending HitogoParamsHolder
     * @see HitogoParamsHolder
     * @since 1.0.0
     */
    protected abstract void onCreateParams(T holder);

    @NonNull
    public HitogoController getController() {
        return controller;
    }
}

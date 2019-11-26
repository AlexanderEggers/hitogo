package org.hitogo.button.core;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;

import org.hitogo.core.HitogoController;

/**
 * This class is used to attach a lifecycle to the button object. Each method will be called by
 * the system in a specific order to structure the code and simplify the usage.
 *
 * @param <T> Generics for the params class which is used to provide builder values to the object.
 * @see Button
 * @see ButtonParams
 * @since 1.0.0
 */
public abstract class ButtonLifecycle<T extends ButtonParams> {

    /**
     * Checks the given parameters for the alert using the ButtonParams object. This method is
     * only executed by the system if the app is operating within a debug state (HitogoController:
     * provideIsDebugState). The check should prevent errors made by the developer and given him
     * more suitable error messages. The onCheck is the first method in this lifecycle which
     * will be called.
     *
     * @param params an object which is extending ButtonParams
     * @see Button
     * @see ButtonParams
     * @see HitogoController
     * @since 1.0.0
     */
    @CallSuper
    protected void onCheck(@NonNull T params) {

    }

    /**
     * This method is executed right after onCheck and should only be used to prepare the button's
     * data and do some pre-calculations.
     *
     * @param params an object which is extending ButtonParams
     * @see Button
     * @see ButtonParams
     * @see HitogoController
     * @since 1.0.0
     */
    @CallSuper
    protected void onCreate(@NonNull T params) {

    }
}

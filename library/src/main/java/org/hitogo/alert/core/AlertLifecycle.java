package org.hitogo.alert.core;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;

import org.hitogo.core.HitogoController;

/**
 * This class is used to attach a lifecycle to the alert object. Each method will be called by
 * the system in a specific order to structure the code and simplify the usage.
 *
 * @param <T> Generics for the params class which is used to provide builder values to the object.
 * @see Alert
 * @see AlertParams
 * @since 1.0.0
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public abstract class AlertLifecycle<T extends AlertParams> {

    /**
     * Checks the given parameters for the alert using the AlertParams object. This method is
     * only executed by the system if the app is operating within a debug state (HitogoController
     * >> provideIsDebugState). The check should prevent errors made by the developer and given him
     * more suitable error messages. The onCheck is the first method in this lifecycle which
     * will be called.
     *
     * @param params AlertParams object which is used to check the values for the alert.
     * @see Alert
     * @see AlertParams
     * @see HitogoController
     * @since 1.0.0
     */
    @CallSuper
    protected void onCheck(@NonNull T params) {

    }

    @CallSuper
    protected void onCreate(@NonNull T params) {

    }

    @CallSuper
    protected void onCreate(@NonNull HitogoController controller, @NonNull T params) {

    }

    @Nullable
    protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull Context context,
                                @NonNull T params) {
        return null;
    }

    @Nullable
    protected Dialog onCreateDialog(@NonNull LayoutInflater inflater, @NonNull Context context,
                                    @NonNull T params) {
        return null;
    }

    @Nullable
    protected PopupWindow onCreatePopup(@NonNull LayoutInflater inflater, @NonNull Context context,
                                        @NonNull T params) {
        return null;
    }

    @CallSuper
    protected void onAttach(@NonNull Context context) {

    }

    @CallSuper
    protected void onShowDefault(@NonNull Context context) {

    }

    @CallSuper
    protected void onShowAnimation(@NonNull Context context) {

    }

    @CallSuper
    protected void onDetach(@NonNull Context context) {

    }

    @CallSuper
    protected void onCloseDefault(@NonNull Context context) {

    }

    @CallSuper
    protected void onCloseAnimation(@NonNull Context context) {

    }
}

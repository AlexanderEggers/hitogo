package org.hitogo.alert.core;

import android.app.Dialog;
import android.content.Context;
import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
public abstract class AlertLifecycle<T extends AlertParams> {

    /**
     * Checks the given parameters for the alert using the AlertParams object. This method is
     * only executed by the system if the app is operating within a debug state (HitogoController:
     * provideIsDebugState). The check should prevent errors made by the developer and given him
     * more suitable error messages. The onCheck is the first method in this lifecycle which
     * will be called.
     *
     * @param params an object which is extending AlertParams
     * @see Alert
     * @see AlertParams
     * @see HitogoController
     * @since 1.0.0
     */
    @CallSuper
    protected void onCheck(@NonNull T params) {

    }

    /**
     * This method is executed right after onCheck and should only be used to prepare the alert's
     * data and do some pre-calculations.
     *
     * @param params an object which is extending AlertParams
     * @see Alert
     * @see AlertParams
     * @since 1.0.0
     */
    @CallSuper
    protected void onCreate(@NonNull T params) {

    }

    /**
     * This method is executed right after onCheck and should only be used to prepare the alert's
     * data and do some pre-calculations.
     *
     * @param params     an object which is extending AlertParams
     * @param controller a HitogoController object
     * @see Alert
     * @see AlertParams
     * @see HitogoController
     * @since 1.0.0
     */
    @CallSuper
    protected void onCreate(@NonNull HitogoController controller, @NonNull T params) {

    }

    /**
     * This method is executed after the onCreate and if the underlying alert is based on the
     * alert type VIEW. At the end of this method the view for this alert should be fully
     * initialised.
     *
     * @param params   an object which is extending AlertParams
     * @param inflater a LayoutInflater object
     * @param context  a Context object
     * @return a View object or null
     * @see Alert
     * @see AlertParams
     * @see LayoutInflater
     * @since 1.0.0
     */
    @Nullable
    protected View onCreateView(LayoutInflater inflater, @NonNull Context context,
                                @NonNull T params) {
        return null;
    }

    /**
     * This method is executed after the onCreate and if the underlying alert is based on the
     * alert type DIALOG. At the end of this method the dialog object for this alert should be fully
     * initialised.
     *
     * @param params   an object which is extending AlertParams
     * @param inflater a LayoutInflater object
     * @param context  a Context object
     * @return a Dialog object or null
     * @see Alert
     * @see AlertParams
     * @see LayoutInflater
     * @since 1.0.0
     */
    @Nullable
    protected Dialog onCreateDialog(LayoutInflater inflater, @NonNull Context context,
                                    @NonNull T params) {
        return null;
    }

    /**
     * This method is executed after the onCreate and if the underlying alert is based on the
     * alert type POPUP. At the end of this method the popup object for this alert should be
     * fully initialised.
     *
     * @param params   an object which is extending AlertParams
     * @param inflater a LayoutInflater object
     * @param context  a Context object
     * @return a PopupWindow object or null
     * @see Alert
     * @see AlertParams
     * @see LayoutInflater
     * @since 1.0.0
     */
    @Nullable
    protected PopupWindow onCreatePopup(LayoutInflater inflater, @NonNull Context context,
                                        @NonNull T params) {
        return null;
    }

    /**
     * This method is executed after the onCreate and if the underlying alert is from AlertType
     * OTHER.
     *
     * @param params   an object which is extending AlertParams
     * @param inflater a LayoutInflater object
     * @param context  a Context object
     * @return an object or null
     * @see Alert
     * @see AlertParams
     * @see LayoutInflater
     * @since 1.0.0
     */
    protected Object onCreateOther(LayoutInflater inflater, @NonNull Context context,
                                   @NonNull T params) {
        return null;
    }

    /**
     * This method is executed when the makeVisible method (AlertImpl) is called and should be used
     * to prepare the displaying of the alert to the user screen.
     *
     * @param context a Context object
     * @see Alert
     * @since 1.0.0
     */
    @CallSuper
    protected void onAttach(@NonNull Context context) {

    }

    /**
     * This method is executed after the onAttach when the makeVisible method (AlertImpl) is
     * called and can be used to execute post-display calculations. This method is only called if
     * the underlying alert has no animations.
     *
     * @param context a Context object
     * @see Alert
     * @since 1.0.0
     */
    @CallSuper
    protected void onShowDefault(@NonNull Context context) {

    }

    /**
     * This method is executed after the onAttach when the makeVisible method (AlertImpl) is
     * called and can be used to show animations for the showing of the alert. This method
     * is only called if the underlying alert has animations.
     *
     * @param context a Context object
     * @see Alert
     * @since 1.0.0
     */
    @CallSuper
    protected void onShowAnimation(@NonNull Context context) {

    }

    /**
     * This method is executed when the makeInvisible method (AlertImpl) is called and should be
     * used to prepare the hiding of the alert.
     *
     * @param context a Context object
     * @see Alert
     * @since 1.0.0
     */
    @CallSuper
    protected void onDetach(@NonNull Context context) {

    }

    /**
     * This method is executed after the onDetach when the makeInvisible method (AlertImpl) is
     * called. This method is only called if the underlying alert has no animations.
     *
     * @param context a Context object
     * @see Alert
     * @since 1.0.0
     */
    @CallSuper
    protected void onCloseDefault(@NonNull Context context) {

    }

    /**
     * This method is executed after the onDetach when the makeInvisible method (AlertImpl) is
     * called. This method is only called if the underlying alert has animations.
     *
     * @param context a Context object
     * @see Alert
     * @since 1.0.0
     */
    @CallSuper
    protected void onCloseAnimation(@NonNull Context context) {

    }
}

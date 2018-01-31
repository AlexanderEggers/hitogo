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

    /**
     * This method is executed right after onCheck and should only be used to prepare the alert's
     * data and do some pre-calculations.
     *
     * @param params AlertParams object which is used to check the values for the alert.
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
     * @param params     AlertParams object which is used to check the values for the alert.
     * @param controller Reference to the used HitogoController for the alert.
     * @see Alert
     * @see AlertParams
     * @see HitogoController
     * @since 1.0.0
     */
    @CallSuper
    protected void onCreate(@NonNull HitogoController controller, @NonNull T params) {

    }

    /**
     * This method is executed after the onCreate and if the underlying alert is based on a view
     * (not to confuse with a popup- or dialog-view). At the end of this method the view for this
     * alert should be fully initialised.
     *
     * @param params   AlertParams object which is used to check the values for the alert.
     * @param inflater LayoutInflater which can be used to retrieve the needed view object.
     * @param context  Context reference to access resources, context-related values or similar.
     * @see Alert
     * @see AlertParams
     * @see LayoutInflater
     * @since 1.0.0
     */
    @Nullable
    protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull Context context,
                                @NonNull T params) {
        return null;
    }

    /**
     * This method is executed after the onCreate and if the underlying alert is based on a dialog.
     * At the end of this method the dialog object for this alert should be fully initialised.
     *
     * @param params   AlertParams object which is used to check the values for the alert.
     * @param inflater LayoutInflater which can be used to retrieve the dialog view object.
     * @param context  Context reference to access resources, context-related values or similar.
     * @see Alert
     * @see AlertParams
     * @see LayoutInflater
     * @since 1.0.0
     */
    @Nullable
    protected Dialog onCreateDialog(@NonNull LayoutInflater inflater, @NonNull Context context,
                                    @NonNull T params) {
        return null;
    }

    /**
     * This method is executed after the onCreate and if the underlying alert is based on a popup.
     * At the end of this method the popup object for this alert should be fully initialised.
     *
     * @param params   AlertParams object which is used to check the values for the alert.
     * @param inflater LayoutInflater which can be used to retrieve the popup view object.
     * @param context  Context reference to access resources, context-related values or similar.
     * @see Alert
     * @see AlertParams
     * @see LayoutInflater
     * @since 1.0.0
     */
    @Nullable
    protected PopupWindow onCreatePopup(@NonNull LayoutInflater inflater, @NonNull Context context,
                                        @NonNull T params) {
        return null;
    }

    /**
     * This method is executed when the makeVisible method (AlertImpl) is called and should be used
     * to prepare the displaying of the alert to the user screen.
     *
     * @param context Context reference to access resources, context-related values or similar.
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
     * @param context Context reference to access resources, context-related values or similar.
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
     * @param context Context reference to access resources, context-related values or similar.
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
     * @param context Context reference to access resources, context-related values or similar.
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
     * @param context Context reference to access resources, context-related values or similar.
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
     * @param context Context reference to access resources, context-related values or similar.
     * @see Alert
     * @since 1.0.0
     */
    @CallSuper
    protected void onCloseAnimation(@NonNull Context context) {

    }
}

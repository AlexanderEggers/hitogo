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

@SuppressWarnings({"WeakerAccess", "unused"})
abstract class AlertLifecycle<T extends AlertParams> {

    @CallSuper
    protected void onCheck(@NonNull T params) {

    }

    @CallSuper
    protected void onCheck(@NonNull HitogoController controller, @NonNull T params) {

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

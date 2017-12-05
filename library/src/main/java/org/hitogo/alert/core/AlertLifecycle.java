package org.hitogo.alert.core;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;

import org.hitogo.core.HitogoController;

@SuppressWarnings({"WeakerAccess", "unused"})
abstract class AlertLifecycle<T extends AlertParams> {

    protected void onCheck(@NonNull T params) {

    }

    protected void onCheck(@NonNull HitogoController controller, @NonNull T params) {

    }

    protected void onCreate(@NonNull T params) {

    }

    protected void onCreate(@NonNull HitogoController controller, @NonNull T params) {

    }

    @Nullable
    protected View onCreateView(@Nullable LayoutInflater inflater, @NonNull Context context,
                                @NonNull T params) {
        return null;
    }

    @Nullable
    protected Dialog onCreateDialog(@Nullable LayoutInflater inflater, @NonNull Context context,
                                    @NonNull T params) {
        return null;
    }

    @Nullable
    protected PopupWindow onCreatePopup(@Nullable LayoutInflater inflater, @NonNull Context context,
                                        @NonNull T params) {
        return null;
    }

    protected void onAttach(@NonNull Context context) {

    }

    protected void onShowDefault(@NonNull Context context) {

    }

    protected void onShowAnimation(@NonNull Context context) {

    }

    protected void onDetach(@NonNull Context context) {

    }

    protected void onCloseDefault(@NonNull Context context) {

    }

    protected void onCloseAnimation(@NonNull Context context) {

    }
}

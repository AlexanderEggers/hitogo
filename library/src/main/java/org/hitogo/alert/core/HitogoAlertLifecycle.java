package org.hitogo.alert.core;

import android.app.Activity;
import android.app.Dialog;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;

import org.hitogo.core.HitogoController;

@SuppressWarnings({"WeakerAccess", "unused"})
abstract class HitogoAlertLifecycle<T extends HitogoAlertParams> {

    protected void onCheck(@NonNull T params) {

    }

    protected void onCheck(@NonNull HitogoController controller, @NonNull T params) {

    }

    protected void onCreate(@NonNull T params) {

    }

    protected void onCreate(@NonNull HitogoController controller, @NonNull T params) {

    }

    @Nullable
    protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull Activity activity,
                                @NonNull T params) {
        return null;
    }

    @Nullable
    protected Dialog onCreateDialog(@NonNull LayoutInflater inflater, @NonNull Activity activity,
                                    @NonNull T params) {
        return null;
    }

    protected void onAttach(@NonNull Activity activity) {

    }

    protected void onShowDefault(@NonNull Activity activity) {

    }

    protected void onShowAnimation(@NonNull Activity activity) {

    }

    protected void onDetach(@NonNull Activity activity) {

    }

    protected void onCloseDefault(@NonNull Activity activity) {

    }

    protected void onCloseAnimation(@NonNull Activity activity) {

    }
}

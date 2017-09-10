package org.hitogo.core;

import android.app.Activity;
import android.app.Dialog;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;

@SuppressWarnings({"WeakerAccess", "unused"})
abstract class HitogoLifecycleCallback<T extends HitogoParams> {

    protected void onCheckStart(@NonNull T params) {

    }

    protected void onCheckStart(@NonNull HitogoController controller, @NonNull T params) {

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
    protected Dialog onCreateDialog(@NonNull Activity activity, @NonNull T params) {
        return null;
    }

    protected void onAttach(@NonNull Activity activity) {

    }

    protected void onShowDefault(@NonNull Activity activity) {

    }

    protected void onShowAnimation(@NonNull Activity activity) {

    }

    protected void onDetachDefault(@NonNull Activity activity) {

    }

    protected void onDetachAnimation(@NonNull Activity activity) {

    }
}

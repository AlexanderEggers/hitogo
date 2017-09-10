package org.hitogo.core;

import android.app.Activity;
import android.app.Dialog;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;

@SuppressWarnings({"WeakerAccess", "unused"})
abstract class HitogoLifecycleCallback<T extends HitogoParams> {

    protected void onCheckStart(Activity activity, @NonNull T params) {

    }

    protected void onCreate(@NonNull T params) {

    }

    protected void onCreate(@NonNull T params, @NonNull HitogoController controller) {

    }

    @Nullable
    protected View onCreateView(@NonNull Activity activity, @NonNull LayoutInflater inflater,
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

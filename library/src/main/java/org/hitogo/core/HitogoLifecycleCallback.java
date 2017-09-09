package org.hitogo.core;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;

@SuppressWarnings({"WeakerAccess", "unused"})
abstract class HitogoLifecycleCallback<T extends HitogoParams> {

    protected void onCheckStart(Context context, @NonNull T params) {

    }

    protected void onCreate(@NonNull T params) {

    }

    protected void onCreate(@NonNull T params, @NonNull HitogoController controller) {

    }

    @Nullable
    protected View onCreateView(@NonNull Context context, @NonNull LayoutInflater inflater,
                                @NonNull T params) {
        return null;
    }

    @Nullable
    protected Dialog onCreateDialog(@NonNull Context context, @NonNull T params) {
        return null;
    }

    protected void onAttach(@NonNull Context context) {

    }

    protected void onShowDefault(@NonNull Context context) {

    }

    protected void onShowAnimation(@NonNull Context context) {

    }

    protected void onDetachDefault(@NonNull Context context) {

    }

    protected void onDetachAnimation(@NonNull Context context) {

    }

    protected final void onGone() {

    }
}

package org.hitogo.core;

import android.support.annotation.NonNull;

import org.hitogo.dialog.HitogoDialog;
import org.hitogo.dialog.HitogoDialogBuilder;
import org.hitogo.dialog.HitogoDialogParams;
import org.hitogo.view.HitogoView;
import org.hitogo.view.HitogoViewBuilder;
import org.hitogo.view.HitogoViewParams;

@SuppressWarnings({"WeakerAccess", "unused"})
public final class Hitogo {

    private Hitogo() {
        //not used
    }

    @NonNull
    public static HitogoViewBuilder asView(@NonNull HitogoContainer container) {
        return new HitogoViewBuilder(HitogoView.class, HitogoViewParams.class, container);
    }

    @NonNull
    public static HitogoViewBuilder asView(@NonNull Class<? extends HitogoObject> targetClass,
                                           @NonNull HitogoContainer container) {
        return new HitogoViewBuilder(targetClass, HitogoViewParams.class, container);
    }

    @NonNull
    public static HitogoDialogBuilder asDialog(@NonNull HitogoContainer container) {
        return new HitogoDialogBuilder(HitogoDialog.class, HitogoDialogParams.class, container);
    }

    @NonNull
    public static HitogoDialogBuilder asDialog(@NonNull Class<? extends HitogoObject> targetClass,
                                               @NonNull HitogoContainer container) {
        return new HitogoDialogBuilder(targetClass, HitogoDialogParams.class, container);
    }
}

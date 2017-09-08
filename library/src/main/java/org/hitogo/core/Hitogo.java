package org.hitogo.core;

import android.support.annotation.NonNull;

import org.hitogo.dialog.HitogoDialog;
import org.hitogo.dialog.HitogoDialogBuilder;
import org.hitogo.error.HitogoErrorBuilder;
import org.hitogo.view.HitogoView;
import org.hitogo.view.HitogoViewBuilder;

@SuppressWarnings({"WeakerAccess", "unused"})
public final class Hitogo {

    private Hitogo() {
        //not used
    }

    @NonNull
    public static HitogoViewBuilder asView(@NonNull HitogoContainer container) {
        return new HitogoViewBuilder(HitogoView.class, container.getActivity(),
                container.getView(), container.getController());
    }

    @NonNull
    public static HitogoViewBuilder asView(@NonNull Class<? extends HitogoObject> targetClass,
                                           @NonNull HitogoContainer container) {
        return new HitogoViewBuilder(targetClass, container.getActivity(), container.getView(),
                container.getController());
    }

    @NonNull
    public static HitogoDialogBuilder asDialog(@NonNull HitogoContainer container) {
        return new HitogoDialogBuilder(HitogoDialog.class, container.getActivity(),
                container.getController());
    }

    @NonNull
    public static HitogoDialogBuilder asDialog(@NonNull Class<? extends HitogoObject> targetClass,
                                               @NonNull HitogoContainer container) {
        return new HitogoDialogBuilder(HitogoDialog.class, container.getActivity(),
                container.getController());
    }

    @NonNull
    public static HitogoErrorBuilder asError() {
        return new HitogoErrorBuilder();
    }
}

package org.hitogo.core;

import android.support.annotation.NonNull;
import android.util.Log;

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
        if(container.getView() == null) {
            Log.d(HitogoView.class.getName(), "Something went wrong with the getView() method " +
                    "for this hitogo container. Are you sure that you attached the view correctly?");
        }
        return new HitogoViewBuilder(container.getActivity(), container.getView(),
                container.getController());
    }

    @NonNull
    public static HitogoViewBuilder asView(@NonNull Class<? extends HitogoObject> targetClass, @NonNull HitogoContainer container) {
        if(container.getView() == null) {
            Log.d(HitogoView.class.getName(), "Something went wrong with the getView() method " +
                    "for this hitogo container. Are you sure that you attached the view correctly?");
        }
        return new HitogoViewBuilder(targetClass, container.getActivity(), container.getView(),
                container.getController());
    }

    @NonNull
    public static HitogoDialogBuilder asDialog(@NonNull HitogoContainer container) {
        return new HitogoDialogBuilder(container.getActivity(), container.getController());
    }

    @NonNull
    public static HitogoErrorBuilder asError() {
        return new HitogoErrorBuilder();
    }
}

package org.hitogo;

import android.support.annotation.NonNull;
import android.util.Log;

@SuppressWarnings({"WeakerAccess", "unused"})
public final class Hitogo {

    private Hitogo() {
        throw new IllegalStateException("Methods can only access by static methods.");
    }

    @NonNull
    public static HitogoBuilder with(@NonNull HitogoContainer container) {
        if(container.getView() == null) {
            Log.d(Hitogo.class.getName(), "Something went wrong with the getView() method " +
                    "for this hitogo container. Are you sure that you attached the view correctly?");
        }

        return new HitogoBuilder(container.getActivity(), container.getView(),
                container.getController());
    }
}

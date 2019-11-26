package org.hitogo.support;

import androidx.lifecycle.Lifecycle;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.View;

import org.hitogo.core.HitogoContainer;
import org.hitogo.core.HitogoController;

/**
 * Fully implemented fragment that is using the HitogoContainer. This class can be used to have
 * certain values pre-defined.
 *
 * @param <T> Class which is extending HitogoController
 * @see HitogoController
 * @since 1.0.0
 */
public abstract class HitogoFragment<T extends HitogoController> extends Fragment implements HitogoContainer {

    private T hitogoController;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        hitogoController = initialiseController(getLifecycle());
    }

    @NonNull
    @Override
    public T getController() {
        return hitogoController;
    }

    @Nullable
    @Override
    public View getActivityView() {
        return getActivity().getWindow().getDecorView().findViewById(android.R.id.content);
    }

    /**
     * Creates a new HitogoController using the given Lifecycle object.
     *
     * @param lifecycle Lifecycle object provided for the HitogoController creation
     * @return a new HitogoController
     * @see HitogoController
     * @since 1.0.0
     */
    @NonNull
    public abstract T initialiseController(@NonNull Lifecycle lifecycle);
}

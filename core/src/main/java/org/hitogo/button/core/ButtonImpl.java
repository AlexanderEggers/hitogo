package org.hitogo.button.core;

import androidx.annotation.NonNull;

import org.hitogo.core.HitogoAccessor;
import org.hitogo.core.HitogoContainer;
import org.hitogo.core.HitogoController;
import org.hitogo.core.HitogoHelper;

import java.lang.ref.WeakReference;

/**
 * This class is used to abstract the lifecycle of an button. The class is extending the
 * ButtonLifecycle to achieve that. Each instance of this class can have a different parameter
 * object which provides values from the builder system.
 *
 * @param <T> Generics for the params class which is used to provide builder values to the object.
 * @see ButtonLifecycle
 * @see ButtonParams
 * @since 1.0.0
 */
public abstract class ButtonImpl<T extends ButtonParams> extends ButtonLifecycle<T> implements Button<T> {

    private WeakReference<HitogoContainer> containerRef;
    private HitogoController controller;
    private HitogoHelper helper;
    private HitogoAccessor accessor;

    private T params;
    private ButtonType buttonType;

    /**
     * Creates the button and starts the internal lifecycle process. This method is only used by
     * the builder system internally.
     *
     * @param container Reference to the HitogoContainer which has been used to
     *                  create this object.
     * @param params    Reference to parameter holder class which is providing the builder values.
     * @return Created button object which can be used to be displayed.
     * @see HitogoContainer
     * @see ButtonParams
     * @since 1.0.0
     */
    protected ButtonImpl<T> create(@NonNull HitogoContainer container, @NonNull T params) {
        this.containerRef = new WeakReference<>(container);
        this.controller = container.getController();
        this.accessor = controller.provideAccessor();
        this.helper = controller.provideHelper();
        this.params = params;
        this.buttonType = params.getButtonType();

        if (getController().provideIsDebugState()) {
            onCheck(params);
        }

        onCreate(params);
        return this;
    }

    /**
     * Returns the used HitogoContainer object for the button.
     *
     * @return HitogoContainer of the button.
     * @see HitogoContainer
     * @since 1.0.0
     */
    @NonNull
    protected HitogoContainer getContainer() {
        return containerRef.get();
    }

    /**
     * Returns the used HitogoController object for the button.
     *
     * @return HitogoController of the button.
     * @see HitogoController
     * @since 1.0.0
     */
    @NonNull
    protected HitogoController getController() {
        return controller;
    }

    /**
     * Returns the used HitogoHelper object for the alert.
     *
     * @return a HitogoHelper object
     * @see HitogoHelper
     * @since 1.0.0
     */
    @NonNull
    protected HitogoHelper getHelper() {
        return helper;
    }

    /**
     * Returns the used HitogoAccessor object for the button.
     *
     * @return a HitogoAccessor object
     * @see HitogoAccessor
     * @since 1.0.0
     */
    @NonNull
    public HitogoAccessor getAccessor() {
        return accessor;
    }

    @Override
    @NonNull
    public T getParams() {
        return params;
    }

    @Override
    @NonNull
    public ButtonType getButtonType() {
        return buttonType;
    }
}

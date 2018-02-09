package org.hitogo.button.core;

import android.support.annotation.NonNull;

import org.hitogo.core.HitogoContainer;
import org.hitogo.core.HitogoController;
import org.hitogo.core.HitogoHelper;

import java.lang.ref.WeakReference;

@SuppressWarnings({"WeakerAccess", "unused"})
public abstract class ButtonImpl<T extends ButtonParams> extends ButtonLifecycle<T> implements Button<T> {

    private WeakReference<HitogoContainer> containerRef;
    private HitogoController controller;
    private HitogoHelper helper;

    private T params;
    private ButtonType buttonType;

    protected ButtonImpl<T> create(@NonNull HitogoContainer container, @NonNull T params) {
        this.containerRef = new WeakReference<>(container);
        this.controller = container.getController();
        this.helper = controller.provideHelper();
        this.params = params;
        this.buttonType = params.getButtonType();

        if(getController().provideIsDebugState()) {
            onCheck(params);
        }

        onCreate(params);
        return this;
    }

    @NonNull
    protected HitogoContainer getContainer() {
        return containerRef.get();
    }

    @NonNull
    protected HitogoController getController() {
        return controller;
    }

    @NonNull
    protected HitogoHelper getHelper() {
        return helper;
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

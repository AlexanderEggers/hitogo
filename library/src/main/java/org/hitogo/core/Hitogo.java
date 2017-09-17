package org.hitogo.core;

import android.support.annotation.NonNull;

import org.hitogo.button.HitogoButtonBuilder;
import org.hitogo.button.HitogoButtonFactory;
import org.hitogo.button.HitogoButtonObject;
import org.hitogo.dialog.HitogoDialogBuilder;
import org.hitogo.dialog.HitogoDialogFactory;
import org.hitogo.view.HitogoViewBuilder;
import org.hitogo.view.HitogoViewFactory;

@SuppressWarnings({"WeakerAccess", "unused"})
public class Hitogo implements HitogoViewFactory<HitogoViewBuilder>,
        HitogoDialogFactory<HitogoDialogBuilder>, HitogoButtonFactory<HitogoButtonBuilder> {

    private HitogoContainer container;
    private HitogoController controller;

    private Hitogo(HitogoContainer container) {
        this.container = container;
        this.controller = container.getController();
    }

    public static Hitogo with(HitogoContainer container) {
        return new Hitogo(container);
    }

    public HitogoViewBuilder asView() {
        return new HitogoViewBuilder(controller.provideDefaultViewClass(),
                controller.provideDefaultViewParamsClass(), container);
    }

    public HitogoViewBuilder asView(@NonNull Class<? extends HitogoObject> targetClass) {
        return new HitogoViewBuilder(
                targetClass, controller.provideDefaultViewParamsClass(), container);
    }

    public HitogoViewBuilder asView(@NonNull Class<? extends HitogoObject> targetClass,
                                           @NonNull Class<? extends HitogoParams> paramClass) {
        return new HitogoViewBuilder(targetClass, paramClass, container);
    }

    public HitogoDialogBuilder asDialog() {
        return new HitogoDialogBuilder(controller.provideDefaultDialogClass(),
                controller.provideDefaultDialogParamsClass(), container);
    }

    public HitogoDialogBuilder asDialog(@NonNull Class<? extends HitogoObject> targetClass) {
        return new HitogoDialogBuilder(targetClass, controller.provideDefaultDialogParamsClass(), container);
    }

    public HitogoDialogBuilder asDialog(@NonNull Class<? extends HitogoObject> targetClass,
                                               @NonNull Class<? extends HitogoParams> paramClass) {
        return new HitogoDialogBuilder(targetClass, paramClass, container);
    }

    @Override
    public HitogoButtonBuilder asButton() {
        return new HitogoButtonBuilder(controller.provideDefaultButtonClass(),
                controller.provideDefaultButtonParamsClass(), container);
    }

    @Override
    public HitogoButtonBuilder asButton(@NonNull Class<? extends HitogoButtonObject> targetClass) {
        return new HitogoButtonBuilder(targetClass, controller.provideDefaultButtonParamsClass(), container);
    }

    @Override
    public HitogoButtonBuilder asButton(@NonNull Class<? extends HitogoButtonObject> targetClass,
                                        @NonNull Class<? extends org.hitogo.button.HitogoParams> paramClass) {
        return new HitogoButtonBuilder(targetClass, paramClass, container);
    }
}

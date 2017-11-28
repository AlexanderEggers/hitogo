package org.hitogo.core;

import android.support.annotation.NonNull;

import org.hitogo.alert.core.HitogoAlert;
import org.hitogo.alert.core.HitogoAlertParams;
import org.hitogo.alert.popup.HitogoPopupBuilder;
import org.hitogo.alert.popup.HitogoPopupFactory;
import org.hitogo.button.action.HitogoActionBuilder;
import org.hitogo.button.action.HitogoActionFactory;
import org.hitogo.button.core.HitogoButton;
import org.hitogo.alert.dialog.HitogoDialogBuilder;
import org.hitogo.alert.dialog.HitogoDialogFactory;
import org.hitogo.alert.view.HitogoViewBuilder;
import org.hitogo.alert.view.HitogoViewFactory;
import org.hitogo.button.core.HitogoButtonParams;

@SuppressWarnings({"WeakerAccess", "unused"})
public class Hitogo implements HitogoViewFactory<HitogoViewBuilder>,
        HitogoDialogFactory<HitogoDialogBuilder>, HitogoActionFactory<HitogoActionBuilder>,
        HitogoPopupFactory<HitogoPopupBuilder>{

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

    public HitogoViewBuilder asView(@NonNull Class<? extends HitogoAlert> targetClass) {
        return new HitogoViewBuilder(
                targetClass, controller.provideDefaultViewParamsClass(), container);
    }

    public HitogoViewBuilder asView(@NonNull Class<? extends HitogoAlert> targetClass,
                                    @NonNull Class<? extends HitogoAlertParams> paramClass) {
        return new HitogoViewBuilder(targetClass, paramClass, container);
    }

    public HitogoDialogBuilder asDialog() {
        return new HitogoDialogBuilder(controller.provideDefaultDialogClass(),
                controller.provideDefaultDialogParamsClass(), container);
    }

    public HitogoDialogBuilder asDialog(@NonNull Class<? extends HitogoAlert> targetClass) {
        return new HitogoDialogBuilder(targetClass, controller.provideDefaultDialogParamsClass(), container);
    }

    public HitogoDialogBuilder asDialog(@NonNull Class<? extends HitogoAlert> targetClass,
                                        @NonNull Class<? extends HitogoAlertParams> paramClass) {
        return new HitogoDialogBuilder(targetClass, paramClass, container);
    }

    @Override
    public HitogoActionBuilder asButton() {
        return new HitogoActionBuilder(controller.provideDefaultButtonClass(),
                controller.provideDefaultButtonParamsClass(), container);
    }

    @Override
    public HitogoActionBuilder asButton(@NonNull Class<? extends HitogoButton> targetClass) {
        return new HitogoActionBuilder(targetClass, controller.provideDefaultButtonParamsClass(), container);
    }

    @Override
    public HitogoActionBuilder asButton(@NonNull Class<? extends HitogoButton> targetClass,
                                        @NonNull Class<? extends HitogoButtonParams> paramClass) {
        return new HitogoActionBuilder(targetClass, paramClass, container);
    }

    @Override
    public HitogoPopupBuilder asPopup() {
        return new HitogoPopupBuilder(controller.provideDefaultPopupClass(),
                controller.provideDefaultPopupParamsClass(), container);
    }

    @Override
    public HitogoPopupBuilder asPopup(@NonNull Class<? extends HitogoAlert> targetClass) {
        return new HitogoPopupBuilder(targetClass, controller.provideDefaultPopupParamsClass(), container);
    }

    @Override
    public HitogoPopupBuilder asPopup(@NonNull Class<? extends HitogoAlert> targetClass,
                                      @NonNull Class<? extends HitogoAlertParams> paramClass) {
        return new HitogoPopupBuilder(targetClass, paramClass, container);
    }
}

package org.hitogo.core;

import android.support.annotation.NonNull;

import org.hitogo.alert.core.AlertImpl;
import org.hitogo.alert.core.AlertParams;
import org.hitogo.alert.popup.PopupAlertBuilder;
import org.hitogo.alert.popup.PopupAlertFactory;
import org.hitogo.button.action.ActionButtonBuilder;
import org.hitogo.button.action.ActionButtonFactory;
import org.hitogo.button.core.ButtonImpl;
import org.hitogo.alert.dialog.DialogAlertBuilder;
import org.hitogo.alert.dialog.DialogAlertFactory;
import org.hitogo.alert.view.ViewAlertBuilder;
import org.hitogo.alert.view.ViewAlertFactory;
import org.hitogo.button.core.ButtonParams;

@SuppressWarnings({"WeakerAccess", "unused"})
public class Hitogo implements ViewAlertFactory<ViewAlertBuilder>, DialogAlertFactory<DialogAlertBuilder>,
        ActionButtonFactory<ActionButtonBuilder>, PopupAlertFactory<PopupAlertBuilder> {

    private HitogoContainer container;
    private HitogoController controller;

    protected Hitogo(@NonNull HitogoContainer container) {
        this.container = container;
        this.controller = container.getController();
    }

    public static Hitogo with(@NonNull HitogoContainer container) {
        return new Hitogo(container);
    }

    public ViewAlertBuilder asViewAlert() {
        return new ViewAlertBuilder(controller.provideDefaultViewClass(),
                controller.provideDefaultViewParamsClass(), container);
    }

    public ViewAlertBuilder asViewAlert(@NonNull Class<? extends AlertImpl> targetClass) {
        return new ViewAlertBuilder(
                targetClass, controller.provideDefaultViewParamsClass(), container);
    }

    public ViewAlertBuilder asViewAlert(@NonNull Class<? extends AlertImpl> targetClass,
                                        @NonNull Class<? extends AlertParams> paramClass) {
        return new ViewAlertBuilder(targetClass, paramClass, container);
    }

    public DialogAlertBuilder asDialogAlert() {
        return new DialogAlertBuilder(controller.provideDefaultDialogClass(),
                controller.provideDefaultDialogParamsClass(), container);
    }

    public DialogAlertBuilder asDialogAlert(@NonNull Class<? extends AlertImpl> targetClass) {
        return new DialogAlertBuilder(targetClass, controller.provideDefaultDialogParamsClass(), container);
    }

    public DialogAlertBuilder asDialogAlert(@NonNull Class<? extends AlertImpl> targetClass,
                                            @NonNull Class<? extends AlertParams> paramClass) {
        return new DialogAlertBuilder(targetClass, paramClass, container);
    }

    @Override
    public ActionButtonBuilder asActionButton() {
        return new ActionButtonBuilder(controller.provideDefaultButtonClass(),
                controller.provideDefaultButtonParamsClass(), container);
    }

    @Override
    public ActionButtonBuilder asActionButton(@NonNull Class<? extends ButtonImpl> targetClass) {
        return new ActionButtonBuilder(targetClass, controller.provideDefaultButtonParamsClass(), container);
    }

    @Override
    public ActionButtonBuilder asActionButton(@NonNull Class<? extends ButtonImpl> targetClass,
                                              @NonNull Class<? extends ButtonParams> paramClass) {
        return new ActionButtonBuilder(targetClass, paramClass, container);
    }

    @Override
    public PopupAlertBuilder asPopupAlert() {
        return new PopupAlertBuilder(controller.provideDefaultPopupClass(),
                controller.provideDefaultPopupParamsClass(), container);
    }

    @Override
    public PopupAlertBuilder asPopupAlert(@NonNull Class<? extends AlertImpl> targetClass) {
        return new PopupAlertBuilder(targetClass, controller.provideDefaultPopupParamsClass(), container);
    }

    @Override
    public PopupAlertBuilder asPopupAlert(@NonNull Class<? extends AlertImpl> targetClass,
                                          @NonNull Class<? extends AlertParams> paramClass) {
        return new PopupAlertBuilder(targetClass, paramClass, container);
    }

    @NonNull
    protected HitogoContainer getContainer() {
        return container;
    }

    @NonNull
    protected HitogoController getController() {
        return controller;
    }
}

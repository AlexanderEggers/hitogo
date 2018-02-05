package org.hitogo.core;

import android.support.annotation.NonNull;

import org.hitogo.alert.core.AlertImpl;
import org.hitogo.alert.core.AlertParams;
import org.hitogo.alert.dialog.DialogAlertBuilder;
import org.hitogo.alert.popup.PopupAlertBuilder;
import org.hitogo.alert.popup.PopupAlertBuilderImpl;
import org.hitogo.alert.popup.PopupAlertFactory;
import org.hitogo.alert.view.ViewAlertBuilder;
import org.hitogo.button.action.ActionButtonBuilder;
import org.hitogo.button.action.ActionButtonBuilderImpl;
import org.hitogo.button.action.ActionButtonFactory;
import org.hitogo.button.core.ButtonImpl;
import org.hitogo.alert.dialog.DialogAlertBuilderImpl;
import org.hitogo.alert.dialog.DialogAlertFactory;
import org.hitogo.alert.view.ViewAlertBuilderImpl;
import org.hitogo.alert.view.ViewAlertFactory;
import org.hitogo.button.core.ButtonParams;

@SuppressWarnings({"WeakerAccess", "unused"})
public class Hitogo implements ViewAlertFactory<ViewAlertBuilder>, DialogAlertFactory<DialogAlertBuilder>,
        ActionButtonFactory<ActionButtonBuilder>, PopupAlertFactory<PopupAlertBuilder> {

    private final HitogoContainer container;
    private final HitogoController controller;

    protected Hitogo(@NonNull HitogoContainer container) {
        this.container = container;
        this.controller = container.getController();
    }

    public static Hitogo with(@NonNull HitogoContainer container) {
        return new Hitogo(container);
    }

    @Override
    public ViewAlertBuilder asViewAlert() {
        return new ViewAlertBuilderImpl(controller.provideDefaultViewClass(),
                controller.provideDefaultViewParamsClass(),
                controller.provideAlertParamsHolder(),
                container);
    }

    @Override
    public ViewAlertBuilder asViewAlert(@NonNull Class<? extends AlertImpl> targetClass) {
        return new ViewAlertBuilderImpl(targetClass,
                controller.provideDefaultViewParamsClass(),
                controller.provideAlertParamsHolder(),
                container);
    }

    @Override
    public ViewAlertBuilder asViewAlert(@NonNull Class<? extends AlertImpl> targetClass,
                                        @NonNull Class<? extends AlertParams> paramClass) {
        return new ViewAlertBuilderImpl(targetClass,
                paramClass,
                controller.provideAlertParamsHolder(),
                container);
    }

    @Override
    public DialogAlertBuilder asDialogAlert() {
        return new DialogAlertBuilderImpl(controller.provideDefaultDialogClass(),
                controller.provideDefaultDialogParamsClass(),
                controller.provideAlertParamsHolder(),
                container);
    }

    @Override
    public DialogAlertBuilder asDialogAlert(@NonNull Class<? extends AlertImpl> targetClass) {
        return new DialogAlertBuilderImpl(targetClass,
                controller.provideDefaultDialogParamsClass(),
                controller.provideAlertParamsHolder(),
                container);
    }

    @Override
    public DialogAlertBuilder asDialogAlert(@NonNull Class<? extends AlertImpl> targetClass,
                                            @NonNull Class<? extends AlertParams> paramClass) {
        return new DialogAlertBuilderImpl(targetClass,
                paramClass,
                controller.provideAlertParamsHolder(),
                container);
    }

    @Override
    public ActionButtonBuilder asActionButton() {
        return new ActionButtonBuilderImpl(controller.provideDefaultButtonClass(),
                controller.provideDefaultButtonParamsClass(),
                controller.provideButtonParamsHolder(),
                container);
    }

    @Override
    public ActionButtonBuilder asActionButton(@NonNull Class<? extends ButtonImpl> targetClass) {
        return new ActionButtonBuilderImpl(targetClass,
                controller.provideDefaultButtonParamsClass(),
                controller.provideButtonParamsHolder(),
                container);
    }

    @Override
    public ActionButtonBuilder asActionButton(@NonNull Class<? extends ButtonImpl> targetClass,
                                                  @NonNull Class<? extends ButtonParams> paramClass) {
        return new ActionButtonBuilderImpl(targetClass,
                paramClass,
                controller.provideButtonParamsHolder(),
                container);
    }

    @Override
    public PopupAlertBuilder asPopupAlert() {
        return new PopupAlertBuilderImpl(controller.provideDefaultPopupClass(),
                controller.provideDefaultPopupParamsClass(),
                controller.provideAlertParamsHolder(),
                container);
    }

    @Override
    public PopupAlertBuilder asPopupAlert(@NonNull Class<? extends AlertImpl> targetClass) {
        return new PopupAlertBuilderImpl(targetClass,
                controller.provideDefaultPopupParamsClass(),
                controller.provideAlertParamsHolder(),
                container);
    }

    @Override
    public PopupAlertBuilder asPopupAlert(@NonNull Class<? extends AlertImpl> targetClass,
                                          @NonNull Class<? extends AlertParams> paramClass) {
        return new PopupAlertBuilderImpl(targetClass,
                paramClass,
                controller.provideAlertParamsHolder(),
                container);
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

package org.hitogo.core;

import android.support.annotation.NonNull;

import org.hitogo.alert.core.AlertImpl;
import org.hitogo.alert.core.AlertParams;
import org.hitogo.alert.dialog.DialogAlertBuilder;
import org.hitogo.alert.popup.PopupAlertBuilder;
import org.hitogo.alert.popup.PopupAlertBuilderImpl;
import org.hitogo.alert.popup.PopupAlertFactory;
import org.hitogo.alert.snackbar.SnackbarAlertBuilder;
import org.hitogo.alert.snackbar.SnackbarAlertBuilderImpl;
import org.hitogo.alert.snackbar.SnackbarAlertFactory;
import org.hitogo.alert.toast.ToastAlertBuilder;
import org.hitogo.alert.toast.ToastAlertBuilderImpl;
import org.hitogo.alert.toast.ToastAlertFactory;
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
import org.hitogo.button.simple.SimpleButtonBuilder;
import org.hitogo.button.simple.SimpleButtonBuilderImpl;
import org.hitogo.button.simple.SimpleButtonFactory;

@SuppressWarnings({"WeakerAccess", "unused"})
public class Hitogo implements ViewAlertFactory<ViewAlertBuilder>, DialogAlertFactory<DialogAlertBuilder>,
        ActionButtonFactory<ActionButtonBuilder>, PopupAlertFactory<PopupAlertBuilder>,
        SnackbarAlertFactory<SnackbarAlertBuilder>, SimpleButtonFactory<SimpleButtonBuilder>,
        ToastAlertFactory<ToastAlertBuilder>{

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
                container);
    }

    @Override
    public ViewAlertBuilder asViewAlert(@NonNull Class<? extends AlertImpl> targetClass) {
        return new ViewAlertBuilderImpl(targetClass,
                controller.provideDefaultViewParamsClass(),
                container);
    }

    @Override
    public ViewAlertBuilder asViewAlert(@NonNull Class<? extends AlertImpl> targetClass,
                                        @NonNull Class<? extends AlertParams> paramClass) {
        return new ViewAlertBuilderImpl(targetClass,
                paramClass,
                container);
    }

    @Override
    public DialogAlertBuilder asDialogAlert() {
        return new DialogAlertBuilderImpl(controller.provideDefaultDialogClass(),
                controller.provideDefaultDialogParamsClass(),
                container);
    }

    @Override
    public DialogAlertBuilder asDialogAlert(@NonNull Class<? extends AlertImpl> targetClass) {
        return new DialogAlertBuilderImpl(targetClass,
                controller.provideDefaultDialogParamsClass(),
                container);
    }

    @Override
    public DialogAlertBuilder asDialogAlert(@NonNull Class<? extends AlertImpl> targetClass,
                                            @NonNull Class<? extends AlertParams> paramClass) {
        return new DialogAlertBuilderImpl(targetClass,
                paramClass,
                container);
    }

    @Override
    public ActionButtonBuilder asActionButton() {
        return new ActionButtonBuilderImpl(controller.provideDefaultActionButtonClass(),
                controller.provideDefaultActionButtonParamsClass(),
                container);
    }

    @Override
    public ActionButtonBuilder asActionButton(@NonNull Class<? extends ButtonImpl> targetClass) {
        return new ActionButtonBuilderImpl(targetClass,
                controller.provideDefaultActionButtonParamsClass(),
                container);
    }

    @Override
    public ActionButtonBuilder asActionButton(@NonNull Class<? extends ButtonImpl> targetClass,
                                              @NonNull Class<? extends ButtonParams> paramClass) {
        return new ActionButtonBuilderImpl(targetClass,
                paramClass,
                container);
    }

    @Override
    public PopupAlertBuilder asPopupAlert() {
        return new PopupAlertBuilderImpl(controller.provideDefaultPopupClass(),
                controller.provideDefaultPopupParamsClass(),
                container);
    }

    @Override
    public PopupAlertBuilder asPopupAlert(@NonNull Class<? extends AlertImpl> targetClass) {
        return new PopupAlertBuilderImpl(targetClass,
                controller.provideDefaultPopupParamsClass(),
                container);
    }

    @Override
    public PopupAlertBuilder asPopupAlert(@NonNull Class<? extends AlertImpl> targetClass,
                                          @NonNull Class<? extends AlertParams> paramClass) {
        return new PopupAlertBuilderImpl(targetClass,
                paramClass,
                container);
    }

    @Override
    public SnackbarAlertBuilder asSnackbarAlert() {
        return new SnackbarAlertBuilderImpl(controller.provideDefaultSnackbarClass(),
                controller.provideDefaultSnackbarParamsClass(),
                container);
    }

    @Override
    public SnackbarAlertBuilder asSnackbarAlert(@NonNull Class<? extends AlertImpl> targetClass) {
        return new SnackbarAlertBuilderImpl(targetClass,
                controller.provideDefaultSnackbarParamsClass(),
                container);
    }

    @Override
    public SnackbarAlertBuilder asSnackbarAlert(@NonNull Class<? extends AlertImpl> targetClass,
                                                @NonNull Class<? extends AlertParams> paramClass) {
        return new SnackbarAlertBuilderImpl(targetClass,
                paramClass,
                container);
    }

    @Override
    public SimpleButtonBuilder asSimpleButton() {
        return new SimpleButtonBuilderImpl(controller.provideDefaultSimpleButtonClass(),
                controller.provideDefaultSimpleButtonParamsClass(),
                container);
    }

    @Override
    public SimpleButtonBuilder asSimpleButton(@NonNull Class<? extends ButtonImpl> targetClass) {
        return new SimpleButtonBuilderImpl(targetClass,
                controller.provideDefaultSimpleButtonParamsClass(),
                container);
    }

    @Override
    public SimpleButtonBuilder asSimpleButton(@NonNull Class<? extends ButtonImpl> targetClass,
                                              @NonNull Class<? extends ButtonParams> paramClass) {
        return new SimpleButtonBuilderImpl(targetClass,
                paramClass,
                container);
    }

    @Override
    public ToastAlertBuilder asToastAlert() {
        return new ToastAlertBuilderImpl(controller.provideDefaultToastClass(),
                controller.provideDefaultToastParamsClass(),
                container);
    }

    @Override
    public ToastAlertBuilder asToastAlert(@NonNull Class<? extends AlertImpl> targetClass) {
        return new ToastAlertBuilderImpl(targetClass,
                controller.provideDefaultToastParamsClass(),
                container);
    }

    @Override
    public ToastAlertBuilder asToastAlert(@NonNull Class<? extends AlertImpl> targetClass,
                                          @NonNull Class<? extends AlertParams> paramClass) {
        return new ToastAlertBuilderImpl(targetClass,
                paramClass,
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

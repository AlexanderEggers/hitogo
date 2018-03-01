package org.hitogo.core;

import android.support.annotation.NonNull;

import org.hitogo.alert.core.AlertImpl;
import org.hitogo.alert.core.AlertParams;
import org.hitogo.alert.dialog.DialogAlertBuilder;
import org.hitogo.alert.dialog.DialogAlertBuilderImpl;
import org.hitogo.alert.dialog.DialogAlertFactory;
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
import org.hitogo.alert.view.ViewAlertBuilderImpl;
import org.hitogo.alert.view.ViewAlertFactory;
import org.hitogo.button.close.CloseButtonFactory;
import org.hitogo.button.core.ButtonImpl;
import org.hitogo.button.core.ButtonParams;
import org.hitogo.button.text.TextButtonBuilder;
import org.hitogo.button.text.TextButtonBuilderImpl;
import org.hitogo.button.text.TextButtonFactory;
import org.hitogo.button.view.ViewButtonBuilder;
import org.hitogo.button.view.ViewButtonBuilderImpl;
import org.hitogo.button.view.ViewButtonFactory;

/**
 * Base Factory class which is used to generate new builders and alerts/buttons. This class can only
 * be initialised by the static method with(HitogoContainer). Each alert/button type has three
 * different methods that accept different input parameter. Each type is implemented by using a
 * separate interface object.
 *
 * @since 1.0.0
 */
public class Hitogo implements ViewAlertFactory<ViewAlertBuilder>, DialogAlertFactory<DialogAlertBuilder>,
        ViewButtonFactory<ViewButtonBuilder>, PopupAlertFactory<PopupAlertBuilder>,
        SnackbarAlertFactory<SnackbarAlertBuilder>, TextButtonFactory<TextButtonBuilder>,
        ToastAlertFactory<ToastAlertBuilder>, CloseButtonFactory<ViewButtonBuilder> {

    private final HitogoContainer container;
    private final HitogoController controller;

    protected Hitogo(@NonNull HitogoContainer container) {
        this.container = container;
        this.controller = container.getController();
    }

    /**
     * Creates a new Hitogo factory class using the given HitogoContainer object. This object
     * includes all relevant objects that are needed for the builder, like config values, views,
     * context etc.
     *
     * @param container a HitogoContainer object
     * @return a new Hitogo factory
     * @see HitogoContainer
     * @since 1.0.0
     */
    @NonNull
    public static Hitogo with(@NonNull HitogoContainer container) {
        return new Hitogo(container);
    }

    @NonNull
    @Override
    public ViewAlertBuilder asViewAlert() {
        return new ViewAlertBuilderImpl(controller.provideDefaultViewClass(),
                controller.provideDefaultViewParamsClass(),
                container);
    }

    @NonNull
    @Override
    public ViewAlertBuilder asViewAlert(@NonNull Class<? extends AlertImpl> targetClass) {
        return new ViewAlertBuilderImpl(targetClass,
                controller.provideDefaultViewParamsClass(),
                container);
    }

    @NonNull
    @Override
    public ViewAlertBuilder asViewAlert(@NonNull Class<? extends AlertImpl> targetClass,
                                        @NonNull Class<? extends AlertParams> paramClass) {
        return new ViewAlertBuilderImpl(targetClass,
                paramClass,
                container);
    }

    @NonNull
    @Override
    public DialogAlertBuilder asDialogAlert() {
        return new DialogAlertBuilderImpl(controller.provideDefaultDialogClass(),
                controller.provideDefaultDialogParamsClass(),
                container);
    }

    @NonNull
    @Override
    public DialogAlertBuilder asDialogAlert(@NonNull Class<? extends AlertImpl> targetClass) {
        return new DialogAlertBuilderImpl(targetClass,
                controller.provideDefaultDialogParamsClass(),
                container);
    }

    @NonNull
    @Override
    public DialogAlertBuilder asDialogAlert(@NonNull Class<? extends AlertImpl> targetClass,
                                            @NonNull Class<? extends AlertParams> paramClass) {
        return new DialogAlertBuilderImpl(targetClass,
                paramClass,
                container);
    }

    @NonNull
    @Override
    public ViewButtonBuilder asViewButton() {
        return new ViewButtonBuilderImpl(controller.provideDefaultViewButtonClass(),
                controller.provideDefaultViewButtonParamsClass(),
                container);
    }

    @NonNull
    @Override
    public ViewButtonBuilder asViewButton(@NonNull Class<? extends ButtonImpl> targetClass) {
        return new ViewButtonBuilderImpl(targetClass,
                controller.provideDefaultViewButtonParamsClass(),
                container);
    }

    @NonNull
    @Override
    public ViewButtonBuilder asViewButton(@NonNull Class<? extends ButtonImpl> targetClass,
                                          @NonNull Class<? extends ButtonParams> paramClass) {
        return new ViewButtonBuilderImpl(targetClass,
                paramClass,
                container);
    }

    @NonNull
    @Override
    public ViewButtonBuilder asCloseButton() {
        return new ViewButtonBuilderImpl(controller.provideDefaultCloseButtonClass(),
                controller.provideDefaultCloseButtonParamsClass(),
                container);
    }

    @NonNull
    @Override
    public ViewButtonBuilder asCloseButton(@NonNull Class<? extends ButtonImpl> targetClass) {
        return new ViewButtonBuilderImpl(targetClass,
                controller.provideDefaultCloseButtonParamsClass(),
                container);
    }

    @NonNull
    @Override
    public ViewButtonBuilder asCloseButton(@NonNull Class<? extends ButtonImpl> targetClass,
                                           @NonNull Class<? extends ButtonParams> paramClass) {
        return new ViewButtonBuilderImpl(targetClass,
                paramClass,
                container);
    }

    @NonNull
    @Override
    public PopupAlertBuilder asPopupAlert() {
        return new PopupAlertBuilderImpl(controller.provideDefaultPopupClass(),
                controller.provideDefaultPopupParamsClass(),
                container);
    }

    @NonNull
    @Override
    public PopupAlertBuilder asPopupAlert(@NonNull Class<? extends AlertImpl> targetClass) {
        return new PopupAlertBuilderImpl(targetClass,
                controller.provideDefaultPopupParamsClass(),
                container);
    }

    @NonNull
    @Override
    public PopupAlertBuilder asPopupAlert(@NonNull Class<? extends AlertImpl> targetClass,
                                          @NonNull Class<? extends AlertParams> paramClass) {
        return new PopupAlertBuilderImpl(targetClass,
                paramClass,
                container);
    }

    @NonNull
    @Override
    public SnackbarAlertBuilder asSnackbarAlert() {
        return new SnackbarAlertBuilderImpl(controller.provideDefaultSnackbarClass(),
                controller.provideDefaultSnackbarParamsClass(),
                container);
    }

    @NonNull
    @Override
    public SnackbarAlertBuilder asSnackbarAlert(@NonNull Class<? extends AlertImpl> targetClass) {
        return new SnackbarAlertBuilderImpl(targetClass,
                controller.provideDefaultSnackbarParamsClass(),
                container);
    }

    @NonNull
    @Override
    public SnackbarAlertBuilder asSnackbarAlert(@NonNull Class<? extends AlertImpl> targetClass,
                                                @NonNull Class<? extends AlertParams> paramClass) {
        return new SnackbarAlertBuilderImpl(targetClass,
                paramClass,
                container);
    }

    @NonNull
    @Override
    public TextButtonBuilder asTextButton() {
        return new TextButtonBuilderImpl(controller.provideDefaultTextButtonClass(),
                controller.provideDefaultTextButtonParamsClass(),
                container);
    }

    @NonNull
    @Override
    public TextButtonBuilder asTextButton(@NonNull Class<? extends ButtonImpl> targetClass) {
        return new TextButtonBuilderImpl(targetClass,
                controller.provideDefaultTextButtonParamsClass(),
                container);
    }

    @NonNull
    @Override
    public TextButtonBuilder asTextButton(@NonNull Class<? extends ButtonImpl> targetClass,
                                          @NonNull Class<? extends ButtonParams> paramClass) {
        return new TextButtonBuilderImpl(targetClass,
                paramClass,
                container);
    }

    @NonNull
    @Override
    public ToastAlertBuilder asToastAlert() {
        return new ToastAlertBuilderImpl(controller.provideDefaultToastClass(),
                controller.provideDefaultToastParamsClass(),
                container);
    }

    @NonNull
    @Override
    public ToastAlertBuilder asToastAlert(@NonNull Class<? extends AlertImpl> targetClass) {
        return new ToastAlertBuilderImpl(targetClass,
                controller.provideDefaultToastParamsClass(),
                container);
    }

    @NonNull
    @Override
    public ToastAlertBuilder asToastAlert(@NonNull Class<? extends AlertImpl> targetClass,
                                          @NonNull Class<? extends AlertParams> paramClass) {
        return new ToastAlertBuilderImpl(targetClass,
                paramClass,
                container);
    }

    /**
     * Returns the used HitogoContainer object for the factory.
     *
     * @return HitogoContainer of the factory.
     * @see HitogoContainer
     * @since 1.0.0
     */
    @NonNull
    protected HitogoContainer getContainer() {
        return container;
    }

    /**
     * Returns the used HitogoController object for the factory.
     *
     * @return HitogoController of the factory.
     * @see HitogoController
     * @since 1.0.0
     */
    @NonNull
    protected HitogoController getController() {
        return controller;
    }
}

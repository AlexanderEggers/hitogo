package org.hitogo.core;

import androidx.annotation.NonNull;

import org.hitogo.alert.DialogAlertBuilderMock;
import org.hitogo.alert.PopupAlertBuilderMock;
import org.hitogo.alert.SnackbarAlertBuilderMock;
import org.hitogo.alert.ToastAlertBuilderMock;
import org.hitogo.alert.ViewAlertBuilderMock;
import org.hitogo.alert.dialog.DialogAlertBuilder;
import org.hitogo.alert.popup.PopupAlertBuilder;
import org.hitogo.alert.snackbar.SnackbarAlertBuilder;
import org.hitogo.alert.toast.ToastAlertBuilder;
import org.hitogo.alert.view.ViewAlertBuilder;
import org.hitogo.button.CloseButtonBuilderMock;
import org.hitogo.button.TextButtonBuilderMock;
import org.hitogo.button.ViewButtonBuilderMock;
import org.hitogo.button.text.TextButtonBuilder;
import org.hitogo.button.view.ViewButtonBuilder;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * A class which can be used to create Mocks for the Hitogo factory.
 *
 * @since 1.0.0
 */
public class HitogoMock {

    /**
     * Creates a new Mock object for the given Hitogo factory.
     *
     * @param hitogo object which is extending Hitogo
     * @return same Hitogo object that has been provided to this method
     * @since 1.0.0
     */
    @NonNull
    public static <T extends Hitogo> T getMock(@NonNull T hitogo) {
        DialogAlertBuilder dialogAlertBuilderMock = DialogAlertBuilderMock.getMock();
        PopupAlertBuilder popupAlertBuilderMock = PopupAlertBuilderMock.getMock();
        SnackbarAlertBuilder snackbarAlertBuilderMock = SnackbarAlertBuilderMock.getMock();
        ToastAlertBuilder toastAlertBuilderMock = ToastAlertBuilderMock.getMock();
        ViewAlertBuilder viewAlertBuilderMock = ViewAlertBuilderMock.getMock();
        ViewButtonBuilder closeButtonBuilderMock = CloseButtonBuilderMock.getMock();
        TextButtonBuilder textButtonBuilderMock = TextButtonBuilderMock.getMock();
        ViewButtonBuilder viewButtonBuilderMock = ViewButtonBuilderMock.getMock();

        return getMock(hitogo, dialogAlertBuilderMock, popupAlertBuilderMock,
                snackbarAlertBuilderMock, toastAlertBuilderMock,
                viewAlertBuilderMock, closeButtonBuilderMock,
                textButtonBuilderMock, viewButtonBuilderMock);
    }

    /**
     * Creates a new Mock object for the Hitogo factory.
     *
     * @return a new Hitogo
     * @since 1.0.0
     */
    @NonNull
    public static Hitogo getMock() {
        DialogAlertBuilder dialogAlertBuilderMock = DialogAlertBuilderMock.getMock();
        PopupAlertBuilder popupAlertBuilderMock = PopupAlertBuilderMock.getMock();
        SnackbarAlertBuilder snackbarAlertBuilderMock = SnackbarAlertBuilderMock.getMock();
        ToastAlertBuilder toastAlertBuilderMock = ToastAlertBuilderMock.getMock();
        ViewAlertBuilder viewAlertBuilderMock = ViewAlertBuilderMock.getMock();
        ViewButtonBuilder closeButtonBuilderMock = CloseButtonBuilderMock.getMock();
        TextButtonBuilder textButtonBuilderMock = TextButtonBuilderMock.getMock();
        ViewButtonBuilder viewButtonBuilderMock = ViewButtonBuilderMock.getMock();

        return getMock(dialogAlertBuilderMock, popupAlertBuilderMock,
                snackbarAlertBuilderMock, toastAlertBuilderMock,
                viewAlertBuilderMock, closeButtonBuilderMock,
                textButtonBuilderMock, viewButtonBuilderMock);
    }

    /**
     * Creates a new Mock object for the given builder objects.
     *
     * @return a new Hitogo
     * @since 1.0.0
     */
    public static Hitogo getMock(DialogAlertBuilder dialogAlertBuilder, PopupAlertBuilder popupAlertBuilder,
                                 SnackbarAlertBuilder snackbarAlertBuilder, ToastAlertBuilder toastAlertBuilder,
                                 ViewAlertBuilder viewAlertBuilder, ViewButtonBuilder closeButtonBuilderMock,
                                 TextButtonBuilder textButtonBuilderMock, ViewButtonBuilder viewButtonBuilderMock) {

        Hitogo hitogo = mock(Hitogo.class);
        return getMock(hitogo, dialogAlertBuilder, popupAlertBuilder, snackbarAlertBuilder, toastAlertBuilder,
                viewAlertBuilder, closeButtonBuilderMock, textButtonBuilderMock, viewButtonBuilderMock);
    }

    /**
     * Creates a new Mock object for the given Hitogo factory and builder objects.
     *
     * @param hitogo object which is exting Hitogo
     * @return same Hitogo object that has been provided to this method
     * @since 1.0.0
     */
    @SuppressWarnings("unchecked")
    public static <T extends Hitogo> T getMock(T hitogo, DialogAlertBuilder dialogAlertBuilder, PopupAlertBuilder popupAlertBuilder,
                                               SnackbarAlertBuilder snackbarAlertBuilder, ToastAlertBuilder toastAlertBuilder,
                                               ViewAlertBuilder viewAlertBuilder, ViewButtonBuilder closeButtonBuilderMock,
                                               TextButtonBuilder textButtonBuilderMock, ViewButtonBuilder viewButtonBuilderMock) {

        when(hitogo.asDialogAlert()).thenReturn(dialogAlertBuilder);
        when(hitogo.asDialogAlert(any(Class.class), any(Class.class))).thenReturn(dialogAlertBuilder);
        when(hitogo.asDialogAlert(any(Class.class))).thenReturn(dialogAlertBuilder);

        when(hitogo.asPopupAlert()).thenReturn(popupAlertBuilder);
        when(hitogo.asPopupAlert(any(Class.class), any(Class.class))).thenReturn(popupAlertBuilder);
        when(hitogo.asPopupAlert(any(Class.class))).thenReturn(popupAlertBuilder);

        when(hitogo.asSnackbarAlert()).thenReturn(snackbarAlertBuilder);
        when(hitogo.asSnackbarAlert(any(Class.class), any(Class.class))).thenReturn(snackbarAlertBuilder);
        when(hitogo.asSnackbarAlert(any(Class.class))).thenReturn(snackbarAlertBuilder);

        when(hitogo.asToastAlert()).thenReturn(toastAlertBuilder);
        when(hitogo.asToastAlert(any(Class.class), any(Class.class))).thenReturn(toastAlertBuilder);
        when(hitogo.asToastAlert(any(Class.class))).thenReturn(toastAlertBuilder);

        when(hitogo.asViewAlert()).thenReturn(viewAlertBuilder);
        when(hitogo.asViewAlert(any(Class.class), any(Class.class))).thenReturn(viewAlertBuilder);
        when(hitogo.asViewAlert(any(Class.class))).thenReturn(viewAlertBuilder);

        when(hitogo.asCloseButton()).thenReturn(closeButtonBuilderMock);
        when(hitogo.asCloseButton(any(Class.class), any(Class.class))).thenReturn(closeButtonBuilderMock);
        when(hitogo.asCloseButton(any(Class.class))).thenReturn(closeButtonBuilderMock);

        when(hitogo.asTextButton()).thenReturn(textButtonBuilderMock);
        when(hitogo.asTextButton(any(Class.class), any(Class.class))).thenReturn(textButtonBuilderMock);
        when(hitogo.asTextButton(any(Class.class))).thenReturn(textButtonBuilderMock);

        when(hitogo.asViewButton()).thenReturn(viewButtonBuilderMock);
        when(hitogo.asViewButton(any(Class.class), any(Class.class))).thenReturn(viewButtonBuilderMock);
        when(hitogo.asViewButton(any(Class.class))).thenReturn(viewButtonBuilderMock);

        return hitogo;
    }
}

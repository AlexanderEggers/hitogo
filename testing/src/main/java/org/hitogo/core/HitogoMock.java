package org.hitogo.core;

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

public class HitogoMock {

    public static Hitogo getMock() {
        return getMock(DialogAlertBuilderMock.getMock(),PopupAlertBuilderMock.getMock(),
                SnackbarAlertBuilderMock.getMock(), ToastAlertBuilderMock.getMock(),
                ViewAlertBuilderMock.getMock(), CloseButtonBuilderMock.getMock(),
                TextButtonBuilderMock.getMock(), ViewButtonBuilderMock.getMock());
    }

    @SuppressWarnings("unchecked")
    public static Hitogo getMock(DialogAlertBuilder dialogAlertBuilder, PopupAlertBuilder popupAlertBuilder,
                                    SnackbarAlertBuilder snackbarAlertBuilder, ToastAlertBuilder toastAlertBuilder,
                                    ViewAlertBuilder viewAlertBuilder, ViewButtonBuilder closeButtonBuilderMock,
                                    TextButtonBuilder textButtonBuilderMock, ViewButtonBuilder viewButtonBuilderMock) {

        Hitogo hitogo = mock(Hitogo.class);

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

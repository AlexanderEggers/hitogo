package org.hitogo.core;

import org.hitogo.alert.dialog.DialogAlertBuilder;
import org.hitogo.alert.popup.PopupAlertBuilder;
import org.hitogo.alert.snackbar.SnackbarAlertBuilder;
import org.hitogo.alert.toast.ToastAlertBuilder;
import org.hitogo.alert.view.ViewAlertBuilder;
import org.hitogo.button.text.TextButtonBuilder;
import org.hitogo.button.view.ViewButtonBuilder;

import static org.mockito.Mockito.when;

@SuppressWarnings("unchecked")
public class HitogoServiceMock {

    public static <T extends HitogoService> T getMock(HitogoService hitogoService) {
        when(hitogoService.create())
                .thenReturn(HitogoMock.getMock());
        return (T) hitogoService;
    }

    public static <T extends HitogoService> T getMock(HitogoService hitogoService, Hitogo hitogo) {
        when(hitogoService.create())
                .thenReturn(HitogoMock.getMock(hitogo));
        return (T) hitogoService;
    }

    public static <T extends HitogoService> T getMock(HitogoService hitogoService,
                                                      DialogAlertBuilder dialogAlertBuilder,
                                                      PopupAlertBuilder popupAlertBuilder,
                                                      SnackbarAlertBuilder snackbarAlertBuilder,
                                                      ToastAlertBuilder toastAlertBuilder,
                                                      ViewAlertBuilder viewAlertBuilder,
                                                      ViewButtonBuilder closeButtonBuilderMock,
                                                      TextButtonBuilder textButtonBuilderMock,
                                                      ViewButtonBuilder viewButtonBuilderMock) {
        when(hitogoService.create())
                .thenReturn(HitogoMock.getMock(dialogAlertBuilder, popupAlertBuilder,
                        snackbarAlertBuilder, toastAlertBuilder, viewAlertBuilder,
                        closeButtonBuilderMock, textButtonBuilderMock, viewButtonBuilderMock));
        return (T) hitogoService;
    }

    public static <T extends HitogoService> T getMock(HitogoService hitogoService,
                                                      Hitogo hitogo,
                                                      DialogAlertBuilder dialogAlertBuilder,
                                                      PopupAlertBuilder popupAlertBuilder,
                                                      SnackbarAlertBuilder snackbarAlertBuilder,
                                                      ToastAlertBuilder toastAlertBuilder,
                                                      ViewAlertBuilder viewAlertBuilder,
                                                      ViewButtonBuilder closeButtonBuilderMock,
                                                      TextButtonBuilder textButtonBuilderMock,
                                                      ViewButtonBuilder viewButtonBuilderMock) {
        when(hitogoService.create())
                .thenReturn(HitogoMock.getMock(hitogo, dialogAlertBuilder, popupAlertBuilder,
                        snackbarAlertBuilder, toastAlertBuilder, viewAlertBuilder,
                        closeButtonBuilderMock, textButtonBuilderMock, viewButtonBuilderMock));
        return (T) hitogoService;
    }
}

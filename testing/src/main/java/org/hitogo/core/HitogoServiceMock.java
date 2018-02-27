package org.hitogo.core;

import org.hitogo.alert.dialog.DialogAlertBuilder;
import org.hitogo.alert.popup.PopupAlertBuilder;
import org.hitogo.alert.snackbar.SnackbarAlertBuilder;
import org.hitogo.alert.toast.ToastAlertBuilder;
import org.hitogo.alert.view.ViewAlertBuilder;
import org.hitogo.button.text.TextButtonBuilder;
import org.hitogo.button.view.ViewButtonBuilder;

import static org.mockito.Mockito.when;

/**
 * A class which can be used to create Mocks for the HitogoService.
 *
 * @see HitogoService
 * @see Hitogo
 * @since 1.0.0
 */
@SuppressWarnings("unchecked")
public class HitogoServiceMock {

    /**
     * Creates a new Mock object for the given HitogoService.
     *
     * @param hitogoService object which is extending HitogoService
     * @return same HitogoService object that has been provided to this method
     * @see HitogoService
     * @since 1.0.0
     */
    public static <T extends HitogoService> T getMock(T hitogoService) {
        when(hitogoService.create())
                .thenReturn(HitogoMock.getMock());
        return hitogoService;
    }

    /**
     * Creates a new Mock object for the given HitogoService and Hitogo factory.
     *
     * @param hitogoService object which is extending HitogoService
     * @param hitogo        a Hitogo factory
     * @return same HitogoService object that has been provided to this method
     * @see HitogoService
     * @since 1.0.0
     */
    public static <T extends HitogoService> T getMock(T hitogoService, Hitogo hitogo) {
        when(hitogoService.create())
                .thenReturn(HitogoMock.getMock(hitogo));
        return hitogoService;
    }

    /**
     * Creates a new Mock object for the given HitogoService and builder objects.
     *
     * @param hitogoService object which is extending HitogoService
     * @return same HitogoService object that has been provided to this method
     * @see HitogoService
     * @since 1.0.0
     */
    public static <T extends HitogoService> T getMock(T hitogoService,
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
        return hitogoService;
    }

    /**
     * Creates a new Mock object for the given HitogoService, Hitogo factory and builder objects.
     *
     * @param hitogoService object which is extending HitogoService
     * @param hitogo        a Hitogo factory
     * @return same HitogoService object that has been provided to this method
     * @see HitogoService
     * @since 1.0.0
     */
    public static <T extends HitogoService> T getMock(T hitogoService,
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
        return hitogoService;
    }
}

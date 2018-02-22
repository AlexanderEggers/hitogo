package org.hitogo.alert;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.design.widget.Snackbar;

import org.hitogo.alert.core.VisibilityListener;
import org.hitogo.alert.snackbar.SnackbarAlert;
import org.hitogo.alert.snackbar.SnackbarAlertBuilder;
import org.hitogo.button.core.Button;
import org.hitogo.core.HitogoController;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SnackbarAlertBuilderMock {

    public static SnackbarAlertBuilder getMock() {
        return getMock(mock(SnackbarAlert.class));
    }

    @SuppressWarnings("unchecked")
    public static SnackbarAlertBuilder getMock(SnackbarAlert alert) {
        SnackbarAlertBuilder snackbarAlertBuilder = mock(SnackbarAlertBuilder.class);

        when(snackbarAlertBuilder.setState(anyInt())).thenReturn(snackbarAlertBuilder);
        when(snackbarAlertBuilder.setState(any(Enum.class))).thenReturn(snackbarAlertBuilder);

        when(snackbarAlertBuilder.addText(anyInt())).thenReturn(snackbarAlertBuilder);
        when(snackbarAlertBuilder.addText(anyString())).thenReturn(snackbarAlertBuilder);

        when(snackbarAlertBuilder.setAction(any(Button.class))).thenReturn(snackbarAlertBuilder);
        when(snackbarAlertBuilder.setBundle(any(Bundle.class))).thenReturn(snackbarAlertBuilder);
        when(snackbarAlertBuilder.setController(any(HitogoController.class))).thenReturn(snackbarAlertBuilder);
        when(snackbarAlertBuilder.setTag(anyString())).thenReturn(snackbarAlertBuilder);
        when(snackbarAlertBuilder.addVisibilityListener(any(VisibilityListener.class))).thenReturn(snackbarAlertBuilder);
        when(snackbarAlertBuilder.setPriority(anyInt())).thenReturn(snackbarAlertBuilder);
        when(snackbarAlertBuilder.addCallback(any(Snackbar.Callback.class))).thenReturn(snackbarAlertBuilder);
        when(snackbarAlertBuilder.setActionTextColor(anyInt())).thenReturn(snackbarAlertBuilder);
        when(snackbarAlertBuilder.setActionTextColor(any(ColorStateList.class))).thenReturn(snackbarAlertBuilder);
        when(snackbarAlertBuilder.setDuration(anyInt())).thenReturn(snackbarAlertBuilder);

        when(snackbarAlertBuilder.build()).thenReturn(alert);

        return snackbarAlertBuilder;
    }
}

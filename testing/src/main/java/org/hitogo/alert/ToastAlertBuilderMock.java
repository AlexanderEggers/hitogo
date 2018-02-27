package org.hitogo.alert;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;

import org.hitogo.alert.core.VisibilityListener;
import org.hitogo.alert.toast.ToastAlert;
import org.hitogo.alert.toast.ToastAlertBuilder;
import org.hitogo.button.core.Button;
import org.hitogo.core.HitogoController;
import org.mockito.Matchers;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyFloat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * A class which can be used to create Mocks for the ToastAlertBuilder.
 *
 * @since 1.0.0
 */
public class ToastAlertBuilderMock {

    /**
     * Creates a new Mock object for the ToastAlertBuilder.
     *
     * @return a new ToastAlertBuilder
     * @since 1.0.0
     */
    @NonNull
    public static ToastAlertBuilder getMock() {
        ToastAlert toastAlertMock = mock(ToastAlert.class);
        return getMock(toastAlertMock);
    }

    /**
     * Creates a new Mock object for the ToastAlertBuilder. The given ToastAlert can be used to
     * define the return value for the method {@code build()}.
     *
     * @param alert a ToastAlert
     * @return a new ToastAlertBuilder
     * @since 1.0.0
     */
    @NonNull
    @SuppressWarnings("unchecked")
    public static ToastAlertBuilder getMock(@NonNull ToastAlert alert) {
        ToastAlertBuilder toastAlertBuilder = mock(ToastAlertBuilder.class);

        when(toastAlertBuilder.addButton(Matchers.<Button>anyVararg())).thenReturn(toastAlertBuilder);

        when(toastAlertBuilder.asDismissible(any(Button.class))).thenReturn(toastAlertBuilder);

        when(toastAlertBuilder.setTitle(anyString())).thenReturn(toastAlertBuilder);
        when(toastAlertBuilder.setTitle(anyInt())).thenReturn(toastAlertBuilder);
        when(toastAlertBuilder.setTitle(anyInt(), anyInt())).thenReturn(toastAlertBuilder);
        when(toastAlertBuilder.setTitle(anyInt(), anyString())).thenReturn(toastAlertBuilder);

        when(toastAlertBuilder.setState(anyInt())).thenReturn(toastAlertBuilder);
        when(toastAlertBuilder.setState(any(Enum.class))).thenReturn(toastAlertBuilder);

        when(toastAlertBuilder.addText(anyInt())).thenReturn(toastAlertBuilder);
        when(toastAlertBuilder.addText(anyString())).thenReturn(toastAlertBuilder);
        when(toastAlertBuilder.addText(anyInt(), anyInt())).thenReturn(toastAlertBuilder);
        when(toastAlertBuilder.addText(anyInt(), anyString())).thenReturn(toastAlertBuilder);

        when(toastAlertBuilder.addDrawable(anyInt())).thenReturn(toastAlertBuilder);
        when(toastAlertBuilder.addDrawable(any(Drawable.class))).thenReturn(toastAlertBuilder);
        when(toastAlertBuilder.addDrawable(anyInt(), anyInt())).thenReturn(toastAlertBuilder);
        when(toastAlertBuilder.addDrawable(anyInt(), any(Drawable.class))).thenReturn(toastAlertBuilder);

        when(toastAlertBuilder.setGravity(anyInt(), anyInt(), anyInt())).thenReturn(toastAlertBuilder);
        when(toastAlertBuilder.setDuration(anyInt())).thenReturn(toastAlertBuilder);
        when(toastAlertBuilder.setMargins(anyFloat(), anyFloat())).thenReturn(toastAlertBuilder);
        when(toastAlertBuilder.setBundle(any(Bundle.class))).thenReturn(toastAlertBuilder);
        when(toastAlertBuilder.setController(any(HitogoController.class))).thenReturn(toastAlertBuilder);
        when(toastAlertBuilder.setLayout(anyInt())).thenReturn(toastAlertBuilder);
        when(toastAlertBuilder.setTag(anyString())).thenReturn(toastAlertBuilder);
        when(toastAlertBuilder.addVisibilityListener(any(VisibilityListener.class))).thenReturn(toastAlertBuilder);
        when(toastAlertBuilder.setPriority(anyInt())).thenReturn(toastAlertBuilder);

        when(toastAlertBuilder.build()).thenReturn(alert);

        return toastAlertBuilder;
    }
}

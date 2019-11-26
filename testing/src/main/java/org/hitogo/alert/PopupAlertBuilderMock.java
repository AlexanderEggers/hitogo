package org.hitogo.alert;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import android.transition.Transition;
import android.view.View;

import org.hitogo.alert.core.VisibilityListener;
import org.hitogo.alert.popup.PopupAlert;
import org.hitogo.alert.popup.PopupAlertBuilder;
import org.hitogo.button.core.Button;
import org.hitogo.core.HitogoController;
import org.mockito.Matchers;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyFloat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * A class which can be used to create Mocks for the PopupAlertBuilder.
 *
 * @since 1.0.0
 */
public class PopupAlertBuilderMock {

    /**
     * Creates a new Mock object for the PopupAlertBuilder.
     *
     * @return a new PopupAlertBuilder
     * @since 1.0.0
     */
    @NonNull
    public static PopupAlertBuilder getMock() {
        PopupAlert popupAlertMock = mock(PopupAlert.class);
        return getMock(popupAlertMock);
    }

    /**
     * Creates a new Mock object for the PopupAlertBuilder. The given PopupAlert can be used to
     * define the return value for the method {@code build()}.
     *
     * @param alert a PopupAlert
     * @return a new PopupAlertBuilder
     * @since 1.0.0
     */
    @NonNull
    @SuppressWarnings("unchecked")
    public static PopupAlertBuilder getMock(@NonNull PopupAlert alert) {
        PopupAlertBuilder popupAlertBuilder = mock(PopupAlertBuilder.class);

        when(popupAlertBuilder.addButton(Matchers.<Button>anyVararg())).thenReturn(popupAlertBuilder);

        when(popupAlertBuilder.asDismissible()).thenReturn(popupAlertBuilder);
        when(popupAlertBuilder.asDismissible(any(Button.class))).thenReturn(popupAlertBuilder);
        when(popupAlertBuilder.asDismissible(anyBoolean())).thenReturn(popupAlertBuilder);

        when(popupAlertBuilder.setAnchor(anyInt())).thenReturn(popupAlertBuilder);
        when(popupAlertBuilder.setAnchor(anyString())).thenReturn(popupAlertBuilder);

        when(popupAlertBuilder.setTitle(anyString())).thenReturn(popupAlertBuilder);
        when(popupAlertBuilder.setTitle(anyInt())).thenReturn(popupAlertBuilder);
        when(popupAlertBuilder.setTitle(anyInt(), anyInt())).thenReturn(popupAlertBuilder);
        when(popupAlertBuilder.setTitle(anyInt(), anyString())).thenReturn(popupAlertBuilder);

        when(popupAlertBuilder.setState(anyInt())).thenReturn(popupAlertBuilder);
        when(popupAlertBuilder.setState(any(Enum.class))).thenReturn(popupAlertBuilder);

        when(popupAlertBuilder.addText(anyInt())).thenReturn(popupAlertBuilder);
        when(popupAlertBuilder.addText(anyString())).thenReturn(popupAlertBuilder);
        when(popupAlertBuilder.addText(anyInt(), anyInt())).thenReturn(popupAlertBuilder);
        when(popupAlertBuilder.addText(anyInt(), anyString())).thenReturn(popupAlertBuilder);

        when(popupAlertBuilder.addDrawable(anyInt())).thenReturn(popupAlertBuilder);
        when(popupAlertBuilder.addDrawable(any(Drawable.class))).thenReturn(popupAlertBuilder);
        when(popupAlertBuilder.addDrawable(anyInt(), anyInt())).thenReturn(popupAlertBuilder);
        when(popupAlertBuilder.addDrawable(anyInt(), any(Drawable.class))).thenReturn(popupAlertBuilder);

        when(popupAlertBuilder.setBundle(any(Bundle.class))).thenReturn(popupAlertBuilder);
        when(popupAlertBuilder.setController(any(HitogoController.class))).thenReturn(popupAlertBuilder);
        when(popupAlertBuilder.setLayout(anyInt())).thenReturn(popupAlertBuilder);
        when(popupAlertBuilder.setTag(anyString())).thenReturn(popupAlertBuilder);
        when(popupAlertBuilder.addVisibilityListener(any(VisibilityListener.class))).thenReturn(popupAlertBuilder);
        when(popupAlertBuilder.asFullscreen(anyBoolean())).thenReturn(popupAlertBuilder);
        when(popupAlertBuilder.dismissByLayoutClick(anyBoolean())).thenReturn(popupAlertBuilder);
        when(popupAlertBuilder.setAnimationStyle(anyInt())).thenReturn(popupAlertBuilder);
        when(popupAlertBuilder.setBackgroundDrawable(anyInt())).thenReturn(popupAlertBuilder);
        when(popupAlertBuilder.setOffset(anyInt(), anyInt())).thenReturn(popupAlertBuilder);
        when(popupAlertBuilder.setSize(anyInt(), anyInt())).thenReturn(popupAlertBuilder);
        when(popupAlertBuilder.setTouchListener(any(View.OnTouchListener.class))).thenReturn(popupAlertBuilder);
        when(popupAlertBuilder.setPriority(anyInt())).thenReturn(popupAlertBuilder);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            when(popupAlertBuilder.setElevation(anyFloat())).thenReturn(popupAlertBuilder);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            when(popupAlertBuilder.setGravity(anyInt())).thenReturn(popupAlertBuilder);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            when(popupAlertBuilder.setTransition(any(Transition.class), any(Transition.class))).thenReturn(popupAlertBuilder);
        }

        when(popupAlertBuilder.build()).thenReturn(alert);

        return popupAlertBuilder;
    }
}

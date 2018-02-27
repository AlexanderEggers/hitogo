package org.hitogo.alert;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;

import org.hitogo.alert.core.VisibilityListener;
import org.hitogo.alert.view.ViewAlert;
import org.hitogo.alert.view.ViewAlertBuilder;
import org.hitogo.button.core.Button;
import org.hitogo.core.HitogoAnimation;
import org.hitogo.core.HitogoController;
import org.mockito.Matchers;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * A class which can be used to create Mocks for the ViewAlertBuilder.
 *
 * @see ViewAlertBuilder
 * @since 1.0.0
 */
public class ViewAlertBuilderMock {

    /**
     * Creates a new Mock object for the ViewAlertBuilder.
     *
     * @return a new ViewAlertBuilder
     * @see ViewAlertBuilder
     * @since 1.0.0
     */
    @NonNull
    public static ViewAlertBuilder getMock() {
        return getMock(mock(ViewAlert.class));
    }

    /**
     * Creates a new Mock object for the ViewAlertBuilder. The given ViewAlert can be used to
     * define the return value for the method {@code build()}.
     *
     * @param alert a ViewAlert
     * @return a new ViewAlertBuilder
     * @see ViewAlertBuilder
     * @see ViewAlert
     * @since 1.0.0
     */
    @NonNull
    @SuppressWarnings("unchecked")
    public static ViewAlertBuilder getMock(@NonNull ViewAlert alert) {
        ViewAlertBuilder viewAlertBuilder = mock(ViewAlertBuilder.class);

        when(viewAlertBuilder.addButton(Matchers.<Button>anyVararg())).thenReturn(viewAlertBuilder);

        when(viewAlertBuilder.asDismissible()).thenReturn(viewAlertBuilder);
        when(viewAlertBuilder.asDismissible(any(Button.class))).thenReturn(viewAlertBuilder);
        when(viewAlertBuilder.asDismissible(anyBoolean())).thenReturn(viewAlertBuilder);

        when(viewAlertBuilder.setTitle(anyString())).thenReturn(viewAlertBuilder);
        when(viewAlertBuilder.setTitle(anyInt())).thenReturn(viewAlertBuilder);
        when(viewAlertBuilder.setTitle(anyInt(), anyInt())).thenReturn(viewAlertBuilder);
        when(viewAlertBuilder.setTitle(anyInt(), anyString())).thenReturn(viewAlertBuilder);

        when(viewAlertBuilder.setState(anyInt())).thenReturn(viewAlertBuilder);
        when(viewAlertBuilder.setState(any(Enum.class))).thenReturn(viewAlertBuilder);

        when(viewAlertBuilder.addText(anyInt())).thenReturn(viewAlertBuilder);
        when(viewAlertBuilder.addText(anyString())).thenReturn(viewAlertBuilder);
        when(viewAlertBuilder.addText(anyInt(), anyInt())).thenReturn(viewAlertBuilder);
        when(viewAlertBuilder.addText(anyInt(), anyString())).thenReturn(viewAlertBuilder);

        when(viewAlertBuilder.asIgnoreLayout()).thenReturn(viewAlertBuilder);
        when(viewAlertBuilder.asLayoutChild()).thenReturn(viewAlertBuilder);
        when(viewAlertBuilder.asLayoutChild(anyInt())).thenReturn(viewAlertBuilder);
        when(viewAlertBuilder.asOverlay()).thenReturn(viewAlertBuilder);
        when(viewAlertBuilder.asOverlay(anyInt())).thenReturn(viewAlertBuilder);

        when(viewAlertBuilder.withAnimations()).thenReturn(viewAlertBuilder);
        when(viewAlertBuilder.withAnimations(anyBoolean())).thenReturn(viewAlertBuilder);
        when(viewAlertBuilder.withAnimations(any(HitogoAnimation.class))).thenReturn(viewAlertBuilder);
        when(viewAlertBuilder.withAnimations(anyInt())).thenReturn(viewAlertBuilder);
        when(viewAlertBuilder.withAnimations(any(HitogoAnimation.class), anyInt())).thenReturn(viewAlertBuilder);

        when(viewAlertBuilder.addDrawable(anyInt())).thenReturn(viewAlertBuilder);
        when(viewAlertBuilder.addDrawable(any(Drawable.class))).thenReturn(viewAlertBuilder);
        when(viewAlertBuilder.addDrawable(anyInt(), anyInt())).thenReturn(viewAlertBuilder);
        when(viewAlertBuilder.addDrawable(anyInt(), any(Drawable.class))).thenReturn(viewAlertBuilder);

        when(viewAlertBuilder.closeOthers(anyBoolean())).thenReturn(viewAlertBuilder);
        when(viewAlertBuilder.dismissByLayoutClick(anyBoolean())).thenReturn(viewAlertBuilder);
        when(viewAlertBuilder.setBundle(any(Bundle.class))).thenReturn(viewAlertBuilder);
        when(viewAlertBuilder.setController(any(HitogoController.class))).thenReturn(viewAlertBuilder);
        when(viewAlertBuilder.setLayout(anyInt())).thenReturn(viewAlertBuilder);
        when(viewAlertBuilder.setTag(anyString())).thenReturn(viewAlertBuilder);
        when(viewAlertBuilder.addVisibilityListener(any(VisibilityListener.class))).thenReturn(viewAlertBuilder);
        when(viewAlertBuilder.setPriority(anyInt())).thenReturn(viewAlertBuilder);

        when(viewAlertBuilder.build()).thenReturn(alert);

        return viewAlertBuilder;
    }
}

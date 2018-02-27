package org.hitogo.button;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;

import org.hitogo.button.core.ButtonListener;
import org.hitogo.button.view.ViewButton;
import org.hitogo.button.view.ViewButtonBuilder;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * A class which can be used to create Mocks for the ViewButtonBuilder.
 *
 * @since 1.0.0
 */
public class ViewButtonBuilderMock {

    /**
     * Creates a new Mock object for the ViewButtonBuilder.
     *
     * @return a new ViewButtonBuilder
     * @since 1.0.0
     */
    @NonNull
    public static ViewButtonBuilder getMock() {
        ViewButton viewButtonMock = mock(ViewButton.class);
        return getMock(viewButtonMock);
    }

    /**
     * Creates a new Mock object for the ViewButtonBuilder. The given ViewButton can be used to
     * define the return value for the method {@code build()}.
     *
     * @param button a ViewButton
     * @return a new ViewButtonBuilder
     * @since 1.0.0
     */
    @NonNull
    public static ViewButtonBuilder getMock(@NonNull ViewButton button) {
        ViewButtonBuilder viewButtonBuilder = mock(ViewButtonBuilder.class);

        when(viewButtonBuilder.addText(anyString())).thenReturn(viewButtonBuilder);
        when(viewButtonBuilder.addText(anyInt())).thenReturn(viewButtonBuilder);
        when(viewButtonBuilder.addText(anyInt(), anyInt())).thenReturn(viewButtonBuilder);
        when(viewButtonBuilder.addText(anyInt(), anyString())).thenReturn(viewButtonBuilder);

        when(viewButtonBuilder.setButtonListener(any(ButtonListener.class))).thenReturn(viewButtonBuilder);
        when(viewButtonBuilder.setButtonListener(any(ButtonListener.class), anyBoolean())).thenReturn(viewButtonBuilder);

        when(viewButtonBuilder.setView(anyInt())).thenReturn(viewButtonBuilder);
        when(viewButtonBuilder.setView(anyInt(), anyInt())).thenReturn(viewButtonBuilder);

        when(viewButtonBuilder.addDrawable(anyInt())).thenReturn(viewButtonBuilder);
        when(viewButtonBuilder.addDrawable(any(Drawable.class))).thenReturn(viewButtonBuilder);
        when(viewButtonBuilder.addDrawable(anyInt(), anyInt())).thenReturn(viewButtonBuilder);
        when(viewButtonBuilder.addDrawable(anyInt(), any(Drawable.class))).thenReturn(viewButtonBuilder);

        when(viewButtonBuilder.build()).thenReturn(button);

        return viewButtonBuilder;
    }
}

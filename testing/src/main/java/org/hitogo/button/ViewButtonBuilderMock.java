package org.hitogo.button;

import android.graphics.drawable.Drawable;

import org.hitogo.button.core.ButtonListener;
import org.hitogo.button.view.ViewButton;
import org.hitogo.button.view.ViewButtonBuilder;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ViewButtonBuilderMock {

    public static ViewButtonBuilder getMock() {
        return getMock(mock(ViewButton.class));
    }

    public static ViewButtonBuilder getMock(ViewButton button) {
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

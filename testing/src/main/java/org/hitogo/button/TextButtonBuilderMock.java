package org.hitogo.button;

import org.hitogo.button.core.ButtonListener;
import org.hitogo.button.text.TextButton;
import org.hitogo.button.text.TextButtonBuilder;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TextButtonBuilderMock {

    public static TextButtonBuilder getMock() {
        return getMock(mock(TextButton.class));
    }

    public static TextButtonBuilder getMock(TextButton button) {
        TextButtonBuilder textButtonBuilder = mock(TextButtonBuilder.class);

        when(textButtonBuilder.addText(anyString())).thenReturn(textButtonBuilder);
        when(textButtonBuilder.addText(anyInt())).thenReturn(textButtonBuilder);

        when(textButtonBuilder.setButtonListener(any(ButtonListener.class))).thenReturn(textButtonBuilder);
        when(textButtonBuilder.setButtonListener(any(ButtonListener.class), anyBoolean())).thenReturn(textButtonBuilder);

        when(textButtonBuilder.build()).thenReturn(button);

        return textButtonBuilder;
    }
}

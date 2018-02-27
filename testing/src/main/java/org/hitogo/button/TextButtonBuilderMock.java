package org.hitogo.button;

import android.support.annotation.NonNull;

import org.hitogo.button.core.ButtonListener;
import org.hitogo.button.text.TextButton;
import org.hitogo.button.text.TextButtonBuilder;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * A class which can be used to create Mocks for the TextButtonBuilder.
 *
 * @since 1.0.0
 */
public class TextButtonBuilderMock {

    /**
     * Creates a new Mock object for the TextButtonBuilder.
     *
     * @return a new TextButtonBuilder
     * @since 1.0.0
     */
    @NonNull
    public static TextButtonBuilder getMock() {
        return getMock(mock(TextButton.class));
    }

    /**
     * Creates a new Mock object for the TextButtonBuilder. The given TextButton can be used to
     * define the return value for the method {@code build()}.
     *
     * @param button a TextButton
     * @return a new TextButtonBuilder
     * @since 1.0.0
     */
    @NonNull
    public static TextButtonBuilder getMock(@NonNull TextButton button) {
        TextButtonBuilder textButtonBuilder = mock(TextButtonBuilder.class);

        when(textButtonBuilder.addText(anyString())).thenReturn(textButtonBuilder);
        when(textButtonBuilder.addText(anyInt())).thenReturn(textButtonBuilder);

        when(textButtonBuilder.setButtonListener(any(ButtonListener.class))).thenReturn(textButtonBuilder);
        when(textButtonBuilder.setButtonListener(any(ButtonListener.class), anyBoolean())).thenReturn(textButtonBuilder);

        when(textButtonBuilder.build()).thenReturn(button);

        return textButtonBuilder;
    }
}

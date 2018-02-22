package org.hitogo.button;

import org.hitogo.button.view.ViewButton;
import org.hitogo.button.view.ViewButtonBuilder;

public class CloseButtonBuilderMock {

    public static ViewButtonBuilder getMock() {
        return ViewButtonBuilderMock.getMock();
    }

    public static ViewButtonBuilder getMock(ViewButton button) {
        return ViewButtonBuilderMock.getMock(button);
    }
}

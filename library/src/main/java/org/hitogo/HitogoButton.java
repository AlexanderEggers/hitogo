package org.hitogo;

import android.support.annotation.NonNull;

@SuppressWarnings({"WeakerAccess", "unused"})
public final class HitogoButton {
    String text;
    Integer[] viewIds;
    HitogoButtonListener listener;
    boolean isCloseButton;
    boolean hasButtonView;

    HitogoButton() {
        //is doing nothing here - just hiding the constructor for other packages
    }

    @NonNull
    public static HitogoButtonBuilder with(@NonNull HitogoContainer container) {
        return new HitogoButtonBuilder(container);
    }

    @NonNull
    static HitogoButtonBuilder with(@NonNull HitogoController controller) {
        return new HitogoButtonBuilder(controller);
    }

    public String getText() {
        return text;
    }

    public Integer[] getViewIds() {
        return viewIds;
    }

    public HitogoButtonListener getListener() {
        return listener;
    }

    public boolean isCloseButton() {
        return isCloseButton;
    }
}

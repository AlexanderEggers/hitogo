package org.hitogo;

import android.support.annotation.NonNull;
import android.view.View;

@SuppressWarnings({"WeakerAccess", "unused"})
public final class HitogoButton {
    String text;
    Integer[] viewIds;
    HitogoButtonListener listener;
    boolean isCloseButton;

    HitogoButton() {
        //is doing nothing here - just hiding the constructor for other packages
    }

    @NonNull
    public static HitogoButtonBuilder with(@NonNull HitogoContainer container) {
        return new HitogoButtonBuilder(container);
    }

    @NonNull
    public static HitogoButtonBuilder with(@NonNull View containerView) {
        return new HitogoButtonBuilder(containerView);
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
}

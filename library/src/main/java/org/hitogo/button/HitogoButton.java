package org.hitogo.button;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.hitogo.core.HitogoContainer;
import org.hitogo.core.HitogoController;

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

    @Nullable
    public String getText() {
        return text;
    }

    @NonNull
    public Integer[] getViewIds() {
        return viewIds;
    }

    @NonNull
    public HitogoButtonListener getListener() {
        return listener;
    }

    public boolean isCloseButton() {
        return isCloseButton;
    }

    public boolean isHasButtonView() {
        return hasButtonView;
    }

    @NonNull
    public static HitogoButtonBuilder with(@NonNull HitogoContainer container) {
        return new HitogoButtonBuilder(container);
    }

    @NonNull
    public static HitogoButtonBuilder with(@NonNull HitogoController controller) {
        return new HitogoButtonBuilder(controller);
    }
}

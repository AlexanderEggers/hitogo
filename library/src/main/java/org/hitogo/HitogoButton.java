package org.hitogo;

@SuppressWarnings({"WeakerAccess", "unused"})
public final class HitogoButton {
    String text;
    Integer[] viewIds;
    HitogoButtonListener listener;
    boolean isCloseButton;

    HitogoButton() {
        //do nothing here - just hide the constructor for other packages
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

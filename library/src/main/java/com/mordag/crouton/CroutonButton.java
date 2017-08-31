package com.mordag.crouton;

public final class CroutonButton {
    String text;
    Integer[] viewIds;
    CroutonButtonListener listener;
    boolean isCloseButton;

    CroutonButton() {
        //do nothing here - just hide the constructor for other packages
    }

    public String getText() {
        return text;
    }

    public Integer[] getViewIds() {
        return viewIds;
    }

    public CroutonButtonListener getListener() {
        return listener;
    }
}

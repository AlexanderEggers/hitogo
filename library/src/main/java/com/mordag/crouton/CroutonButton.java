package com.mordag.crouton;

public final class CroutonButton {
    final CroutonButtonListener defaultListener = new CroutonButtonListener() {
        @Override
        public void onClick() {
            //do nothing if using default
        }
    };
    String text;
    int viewId;
    CroutonButtonListener listener = defaultListener;

    CroutonButton() {
        //do nothing here - just hide the constructor for other packages
    }

    public String getText() {
        return text;
    }

    public int getViewId() {
        return viewId;
    }

    public CroutonButtonListener getListener() {
        return listener;
    }
}

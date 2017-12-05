package org.hitogo.alert.core;

@SuppressWarnings({"WeakerAccess", "unused"})
public abstract class VisibilityAdapter implements VisibilityListener {

    @Override
    public void onCreate(Alert object) {
        //implementation optional
    }

    @Override
    public void onShow(Alert object) {
        //implementation optional
    }

    @Override
    public void onClose(Alert object) {
        //implementation optional
    }
}

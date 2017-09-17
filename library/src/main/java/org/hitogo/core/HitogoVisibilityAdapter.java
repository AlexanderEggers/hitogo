package org.hitogo.core;

@SuppressWarnings({"WeakerAccess", "unused"})
public abstract class HitogoVisibilityAdapter implements HitogoVisibilityListener {

    public void onShow() {
        //implementation optional
    }

    public void onClose() {
        //implementation optional
    }
}

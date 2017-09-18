package org.hitogo.core;

@SuppressWarnings({"WeakerAccess", "unused"})
public abstract class HitogoVisibilityAdapter implements HitogoVisibilityListener {

    @Override
    public void onCreate(HitogoObject object) {
        //implementation optional
    }

    public void onShow(HitogoObject object) {
        //implementation optional
    }

    public void onClose(HitogoObject object) {
        //implementation optional
    }
}

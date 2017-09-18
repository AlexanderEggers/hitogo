package org.hitogo.core;

public interface HitogoVisibilityListener {
    void onCreate(HitogoObject object);
    void onShow(HitogoObject object);
    void onClose(HitogoObject object);
}

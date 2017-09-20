package org.hitogo.alert.core;

public interface HitogoVisibilityListener {
    void onCreate(HitogoAlert object);
    void onShow(HitogoAlert object);
    void onClose(HitogoAlert object);
}

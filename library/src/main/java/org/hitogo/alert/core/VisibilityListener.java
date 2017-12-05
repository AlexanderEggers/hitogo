package org.hitogo.alert.core;

public interface VisibilityListener {
    void onCreate(Alert object);
    void onShow(Alert object);
    void onClose(Alert object);
}

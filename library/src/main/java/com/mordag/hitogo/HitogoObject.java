package com.mordag.hitogo;

import android.app.Activity;
import android.support.annotation.NonNull;

@SuppressWarnings({"WeakerAccess", "unused"})
public abstract class HitogoObject {
    private HitogoController controller;

    protected HitogoObject(@NonNull HitogoController controller) {
        this.controller = controller;
    }

    public final void show(@NonNull Activity activity) {
        HitogoObject object = controller.validate(this);
        if(!object.isVisible()) {
            object.makeVisible(activity);
        }
    }

    protected abstract void makeVisible(@NonNull Activity activity);
    protected abstract void hide();
    protected abstract boolean isVisible();

    public abstract int hashCode();
    public abstract boolean equals(@NonNull Object obj);
}

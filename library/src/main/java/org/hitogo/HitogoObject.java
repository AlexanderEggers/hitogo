package org.hitogo;

import android.app.Activity;
import android.support.annotation.NonNull;

@SuppressWarnings({"WeakerAccess", "unused"})
public abstract class HitogoObject {
    private HitogoController controller;
    private int hashCode;

    protected HitogoObject(@NonNull HitogoController controller, int hashCode) {
        this.controller = controller;
        this.hashCode = hashCode;
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

    @Override
    public final int hashCode() {
        return hashCode;
    }

    @Override
    public final boolean equals(@NonNull Object obj) {
        return obj instanceof HitogoObject && this.hashCode == obj.hashCode();
    }
}

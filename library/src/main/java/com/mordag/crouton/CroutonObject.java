package com.mordag.crouton;

import android.app.Activity;

public abstract class CroutonObject {
    private CroutonController controller;

    CroutonObject(CroutonController controller) {
        this.controller = controller;
    }

    public void show(Activity activity) {
        CroutonObject object = controller.validate(this);
        if(object != null && !object.isVisible()) {
            object.makeVisible(activity);
        }
    }

    abstract void makeVisible(Activity activity);
    abstract void hide();
    abstract boolean isVisible();
    public abstract int hashCode();
    public abstract boolean equals(Object obj);
}

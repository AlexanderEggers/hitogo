package com.mordag.crouton;

import android.support.annotation.Nullable;

public abstract class CroutonController {

    private CroutonObject crouton;

    public final CroutonObject validate(CroutonObject crouton) {
        if(this.crouton == null || !this.crouton.equals(crouton)) {
            if(this.crouton != null) {
                this.crouton.hide();
            }
            this.crouton = crouton;
            return crouton;
        }
        return this.crouton;
    }

    public final void hide() {
        if(crouton != null && crouton.isVisible()) {
            crouton.hide();
        }
    }

    public abstract int getLayout(int state);
    public abstract int getDefaultState();
    public abstract int getLayoutContainerId();
    public abstract int getOverlayContainerId();
    public abstract int getDefaultCloseIconId();
    public abstract int getDefaultTextViewId();
    public abstract int getDefaultTitleViewId();
    @Nullable public abstract Integer getDefaultCloseClickId();
    @Nullable public abstract CroutonAnimation getCroutonAnimation();
}

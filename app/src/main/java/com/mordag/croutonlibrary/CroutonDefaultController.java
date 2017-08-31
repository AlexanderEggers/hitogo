package com.mordag.croutonlibrary;

import android.support.annotation.Nullable;

import com.mordag.crouton.CroutonAnimation;
import com.mordag.crouton.CroutonController;
import com.mordag.crouton.R;

public class CroutonDefaultController extends CroutonController {

    public static final int HINT = 0;
    public static final int SUCCESS = 1;
    public static final int WARNING = 2;
    public static final int DANGER = 3;

    public static int getDefaultButtonId() {
        return R.id.button;
    }

    public static int getDefaultButtonLinkId() {
        return R.id.button_link;
    }

    @Override
    public int getLayout(int state) {
        switch (state) {
            case SUCCESS:
                return R.layout.o2theme_crouton_success;
            case WARNING:
                return R.layout.o2theme_crouton_warning;
            case DANGER:
                return R.layout.o2theme_crouton_danger;
            case HINT:
            default:
                return R.layout.o2theme_crouton_hint;
        }
    }

    @Override
    public int getDefaultState() {
        return HINT;
    }

    @Override
    public int getLayoutContainerId() {
        return R.id.crouton_container;
    }

    @Override
    public int getOverlayContainerId() {
        return R.id.crouton_overlay_container;
    }

    @Override
    public int getDefaultCloseIconId() {
        return R.id.remove;
    }

    @Override
    public int getDefaultTextViewId() {
        return 0;
    }

    @Override
    public int getDefaultTitleViewId() {
        return 0;
    }

    @Nullable
    @Override
    public Integer getDefaultCloseClickId() {
        return R.id.clickRemove;
    }

    @Nullable
    @Override
    public CroutonAnimation getCroutonAnimation() {
        return new CroutonDefaultAnimation();
    }
}

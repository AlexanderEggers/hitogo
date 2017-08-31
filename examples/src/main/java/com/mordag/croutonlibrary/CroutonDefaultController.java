package com.mordag.croutonlibrary;

import android.support.annotation.Nullable;

import com.mordag.crouton.CroutonAnimation;
import com.mordag.crouton.CroutonController;
import com.mordag.crouton.CroutonDefaultAnimation;

public class CroutonDefaultController extends CroutonController {

    public static final int HINT = 0;
    public static final int SUCCESS = 1;
    public static final int WARNING = 2;
    public static final int DANGER = 3;

    public static int getDefaultButtonId() {
        return 0;
    }

    public static int getDefaultButtonLinkId() {
        return 0;
    }

    @Override
    public int getLayout(int state) {
        /*switch (state) {
            case SUCCESS:
                return R.layout.o2theme_crouton_success;
            case WARNING:
                return R.layout.o2theme_crouton_warning;
            case DANGER:
                return R.layout.o2theme_crouton_danger;
            case HINT:
            default:
                return R.layout.o2theme_crouton_hint;
        }*/
        return 0;
    }

    @Override
    public int getDefaultState() {
        return HINT;
    }

    @Override
    public int getLayoutContainerId() {
        return 0;
    }

    @Override
    public int getOverlayContainerId() {
        return 0;
    }

    @Override
    public int getDefaultCloseIconId() {
        return 0;
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
        return 0;
    }

    @Nullable
    @Override
    public CroutonAnimation getDefaultAnimation() {
        return new CroutonDefaultAnimation();
    }
}

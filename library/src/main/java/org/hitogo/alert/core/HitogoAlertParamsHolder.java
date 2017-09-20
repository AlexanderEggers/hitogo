package org.hitogo.alert.core;

import org.hitogo.alert.view.anim.HitogoAnimation;
import org.hitogo.button.core.HitogoButton;
import org.hitogo.core.HitogoParamsHolder;

import java.util.List;

public class HitogoAlertParamsHolder extends HitogoParamsHolder {

    private HitogoAnimation hitogoAnimation;
    private List<HitogoButton> callToActionButtons;
    private HitogoButton closeButton;
    private HitogoVisibilityListener listener;

    public final void provideAnimation(HitogoAnimation animation) {
        this.hitogoAnimation = animation;
    }

    public final void provideVisibilityListener(HitogoVisibilityListener listener) {
        this.listener = listener;
    }

    public final void provideCallToActionButtons(List<HitogoButton> buttonList) {
        this.callToActionButtons = buttonList;
    }

    public final void provideCloseButton(HitogoButton button) {
        this.closeButton = button;
    }

    HitogoAnimation getAnimation() {
        return hitogoAnimation;
    }

    List<HitogoButton> getCallToActionButtons() {
        return callToActionButtons;
    }

    HitogoButton getCloseButton() {
        return closeButton;
    }

    HitogoVisibilityListener getVisibilityListener() {
        return listener;
    }
}

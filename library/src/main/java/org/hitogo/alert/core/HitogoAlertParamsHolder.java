package org.hitogo.alert.core;

import android.util.SparseArray;

import org.hitogo.alert.view.anim.HitogoAnimation;
import org.hitogo.button.core.HitogoButton;
import org.hitogo.core.HitogoParamsHolder;

import java.util.List;

public class HitogoAlertParamsHolder extends HitogoParamsHolder {

    private HitogoAnimation hitogoAnimation;
    private List<HitogoButton> callToActionButtons;
    private HitogoButton closeButton;
    private HitogoVisibilityListener listener;
    private SparseArray<String> textMap;

    public final void provideAnimation(HitogoAnimation animation) {
        this.hitogoAnimation = animation;
    }

    public final void provideVisibilityListener(HitogoVisibilityListener listener) {
        this.listener = listener;
    }

    public final void provideButtons(List<HitogoButton> buttonList) {
        this.callToActionButtons = buttonList;
    }

    void provideTextMap(SparseArray<String> textMap) {
        this.textMap = textMap;
    }

    SparseArray<String> getTextMap() {
        return textMap;
    }

    final void provideCloseButton(HitogoButton button) {
        this.closeButton = button;
    }

    HitogoAnimation getAnimation() {
        return hitogoAnimation;
    }

    List<HitogoButton> getButtons() {
        return callToActionButtons;
    }

    HitogoButton getCloseButton() {
        return closeButton;
    }

    HitogoVisibilityListener getVisibilityListener() {
        return listener;
    }
}

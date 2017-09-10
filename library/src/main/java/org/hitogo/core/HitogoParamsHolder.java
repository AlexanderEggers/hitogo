package org.hitogo.core;

import android.os.Bundle;

import org.hitogo.button.HitogoButtonObject;

import java.util.List;

public class HitogoParamsHolder {

    private Bundle bundle = new Bundle();

    private HitogoAnimation hitogoAnimation;
    private List<HitogoButtonObject> callToActionButtons;
    private HitogoButtonObject closeButton;

    public final String getString(String key) {
        return bundle.getString(key);
    }

    public final Integer getInteger(String key) {
        return (Integer) bundle.getSerializable(key);
    }

    public final Boolean getBoolean(String key) {
        return bundle.getBoolean(key);
    }

    public final Bundle getExtras(String key) {
        return bundle.getBundle(key);
    }

    public final void provideString(String key, String value) {
        bundle.putString(key, value);
    }

    public final void provideInteger(String key, Integer value) {
        bundle.putSerializable(key, value);
    }

    public final void provideBoolean(String key, Boolean value) {
        bundle.putSerializable(key, value);
    }

    public final void provideExtras(String key, Bundle extras) {
        bundle.putBundle(key, extras);
    }

    public final void provideAnimation(HitogoAnimation animation) {
        this.hitogoAnimation = animation;
    }

    public final void provideCallToActionButtons(List<HitogoButtonObject> buttonList) {
        this.callToActionButtons = buttonList;
    }

    public final void provideCloseButton(HitogoButtonObject button) {
        this.closeButton = button;
    }

    HitogoAnimation getAnimation() {
        return hitogoAnimation;
    }

    List<HitogoButtonObject> getCallToActionButtons() {
        return callToActionButtons;
    }

    HitogoButtonObject getCloseButton() {
        return closeButton;
    }
}

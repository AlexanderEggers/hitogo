package org.hitogo.core;

import android.os.Bundle;

import org.hitogo.button.HitogoButton;

import java.util.List;

public abstract class HitogoParams {

    private int hashCode;
    private HitogoObject.HitogoType type;

    private HitogoAnimation hitogoAnimation;
    private List<HitogoButton> callToActionButtons;
    private HitogoButton closeButton;

    public HitogoParams(HitogoBuilder builder, Bundle publicBundle, Bundle privateBundle) {
        hashCode = privateBundle.getInt("hashCode");
        type = (HitogoObject.HitogoType) privateBundle.getSerializable("type");

        hitogoAnimation = builder.getHitogoAnimation();
        callToActionButtons = builder.getCallToActionButtons();
        closeButton = builder.getCloseButton();

        onCreateParams(publicBundle);
    }

    protected abstract void onCreateParams(Bundle bundle);

    int getHashCode() {
        return hashCode;
    }

    HitogoObject.HitogoType getType() {
        return type;
    }

    public HitogoAnimation getHitogoAnimation() {
        return hitogoAnimation;
    }

    public List<HitogoButton> getCallToActionButtons() {
        return callToActionButtons;
    }

    public HitogoButton getCloseButton() {
        return closeButton;
    }

    public abstract boolean hasAnimation();
}

package org.hitogo.core;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.hitogo.button.HitogoButton;

import java.util.List;

@SuppressWarnings({"WeakerAccess", "unused"})
public abstract class HitogoParams {

    private int hashCode;
    private HitogoObject.HitogoType type;
    private String tag;

    private HitogoAnimation hitogoAnimation;
    private List<HitogoButton> callToActionButtons;
    private HitogoButton closeButton;

    void provideData(HitogoParamsHolder holder, Bundle privateBundle) {
        hitogoAnimation = holder.getAnimation();
        callToActionButtons = holder.getCallToActionButtons();
        closeButton = holder.getCloseButton();

        tag = privateBundle.getString("tag");
        hashCode = privateBundle.getInt("hashCode");
        type = (HitogoObject.HitogoType) privateBundle.getSerializable("type");

        onCreateParams(holder);
    }

    protected abstract void onCreateParams(HitogoParamsHolder holder);

    public final int getHashCode() {
        return hashCode;
    }

    public final HitogoObject.HitogoType getType() {
        return type;
    }

    public final String getTag() {
        return tag;
    }

    @Nullable
    public final HitogoAnimation getAnimation() {
        return hitogoAnimation;
    }

    @NonNull
    public final List<HitogoButton> getCallToActionButtons() {
        return callToActionButtons;
    }

    @Nullable
    public final HitogoButton getCloseButton() {
        return closeButton;
    }

    public abstract boolean hasAnimation();
}

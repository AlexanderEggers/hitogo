package org.hitogo.core;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.hitogo.button.HitogoButtonObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@SuppressWarnings({"WeakerAccess", "unused"})
public abstract class HitogoParams {

    private int hashCode;
    private HitogoObject.HitogoType type;
    private String tag;

    private HitogoAnimation hitogoAnimation;
    private List<HitogoButtonObject> callToActionButtons;
    private HitogoButtonObject closeButton;

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
    public abstract boolean hasAnimation();

    public final int getHashCode() {
        return hashCode;
    }

    @NonNull
    public final HitogoObject.HitogoType getType() {
        return type;
    }

    @NonNull
    public final String getTag() {
        return tag;
    }

    @Nullable
    public final HitogoAnimation getAnimation() {
        return hitogoAnimation;
    }

    @NonNull
    public final List<HitogoButtonObject> getCallToActionButtons() {
        return callToActionButtons != null ? callToActionButtons : new ArrayList<HitogoButtonObject>();
    }

    @Nullable
    public final HitogoButtonObject getCloseButton() {
        return closeButton;
    }
}

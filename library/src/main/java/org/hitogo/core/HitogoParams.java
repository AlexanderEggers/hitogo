package org.hitogo.core;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.hitogo.button.HitogoButtonObject;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"WeakerAccess", "unused"})
public abstract class HitogoParams {

    private String tag;
    private int hashCode;
    private Integer state;
    private HitogoType type;
    private Bundle arguments;

    private HitogoAnimation hitogoAnimation;
    private List<HitogoButtonObject> callToActionButtons;
    private HitogoButtonObject closeButton;
    private HitogoVisibilityListener listener;

    void provideData(HitogoParamsHolder holder, Bundle privateBundle) {
        hitogoAnimation = holder.getAnimation();
        callToActionButtons = holder.getCallToActionButtons();
        closeButton = holder.getCloseButton();
        listener = holder.getVisibilityListener();

        tag = privateBundle.getString("tag");
        hashCode = privateBundle.getInt("hashCode");
        arguments = privateBundle.getBundle("arguments");
        type = (HitogoType) privateBundle.getSerializable("type");
        state = (Integer) privateBundle.getSerializable("state");

        onCreateParams(holder);
    }

    protected abstract void onCreateParams(HitogoParamsHolder holder);

    public final boolean hasAnimation() {
        return hitogoAnimation != null;
    }

    public final int getHashCode() {
        return hashCode;
    }

    public boolean isClosingOthers() {
        return true;
    }

    @NonNull
    public final HitogoType getType() {
        return type;
    }

    @NonNull
    public final String getTag() {
        return tag;
    }

    public final Integer getState() {
        return state;
    }

    @Nullable
    public final HitogoAnimation getAnimation() {
        return hitogoAnimation;
    }

    @NonNull
    public final List<HitogoButtonObject> getCallToActionButtons() {
        return callToActionButtons != null ? callToActionButtons : new ArrayList<HitogoButtonObject>();
    }

    public final Bundle getArguments() {
        return arguments;
    }

    public final HitogoButtonObject getCloseButton() {
        return closeButton;
    }

    public HitogoVisibilityListener getVisibilityListener() {
        return listener;
    }

    public boolean consumeLayoutClick() {
        return false;
    }
}

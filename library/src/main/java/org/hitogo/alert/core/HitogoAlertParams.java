package org.hitogo.alert.core;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.hitogo.alert.view.anim.HitogoAnimation;
import org.hitogo.button.core.HitogoButton;
import org.hitogo.core.HitogoParams;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"WeakerAccess", "unused"})
public abstract class HitogoAlertParams extends HitogoParams<HitogoAlertParamsHolder> {

    private String tag;
    private int hashCode;
    private Integer state;
    private HitogoAlertType type;
    private Bundle arguments;

    private HitogoAnimation hitogoAnimation;
    private List<HitogoButton> callToActionButtons;
    private HitogoButton closeButton;
    private HitogoVisibilityListener listener;

    @Override
    protected void provideData(HitogoAlertParamsHolder holder, Bundle privateBundle) {
        hitogoAnimation = holder.getAnimation();
        callToActionButtons = holder.getCallToActionButtons();
        closeButton = holder.getCloseButton();
        listener = holder.getVisibilityListener();

        tag = privateBundle.getString("tag");
        hashCode = privateBundle.getInt("hashCode");
        arguments = privateBundle.getBundle("arguments");
        type = (HitogoAlertType) privateBundle.getSerializable("type");
        state = (Integer) privateBundle.getSerializable("state");

        onCreateParams(holder);
    }

    protected abstract void onCreateParams(HitogoAlertParamsHolder holder);

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
    public final HitogoAlertType getType() {
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
    public final List<HitogoButton> getCallToActionButtons() {
        return callToActionButtons != null ? callToActionButtons : new ArrayList<HitogoButton>();
    }

    public final Bundle getArguments() {
        return arguments;
    }

    public final HitogoButton getCloseButton() {
        return closeButton;
    }

    public HitogoVisibilityListener getVisibilityListener() {
        return listener;
    }

    public boolean consumeLayoutClick() {
        return false;
    }
}

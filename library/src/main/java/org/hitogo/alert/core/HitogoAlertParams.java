package org.hitogo.alert.core;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.SparseArray;

import org.hitogo.alert.view.anim.HitogoAnimation;
import org.hitogo.button.core.HitogoButton;
import org.hitogo.core.HitogoParams;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"WeakerAccess", "unused"})
public abstract class HitogoAlertParams extends HitogoParams<HitogoAlertParamsHolder> {

    private String title;
    private Integer titleViewId;
    private String tag;
    private int hashCode;
    private Integer state;
    private HitogoAlertType type;
    private Bundle arguments;

    private SparseArray<String> textMap;
    private HitogoAnimation hitogoAnimation;
    private List<HitogoButton> buttons;
    private HitogoButton closeButton;
    private HitogoVisibilityListener listener;

    @Override
    protected void provideData(HitogoAlertParamsHolder holder, Bundle privateBundle) {
        hitogoAnimation = holder.getAnimation();
        buttons = holder.getButtons();
        closeButton = holder.getCloseButton();
        listener = holder.getVisibilityListener();
        textMap = holder.getTextMap();

        title = privateBundle.getString("title");
        titleViewId = (Integer) privateBundle.getSerializable("titleViewId");
        tag = privateBundle.getString("tag");
        hashCode = privateBundle.getInt("hashCode");
        arguments = privateBundle.getBundle("arguments");
        type = (HitogoAlertType) privateBundle.getSerializable("type");
        state = (Integer) privateBundle.getSerializable("state");

        onCreateParams(holder);
    }

    protected abstract void onCreateParams(HitogoAlertParamsHolder holder);

    public String getTitle() {
        return title;
    }

    public Integer getTitleViewId() {
        return titleViewId;
    }

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
    public final List<HitogoButton> getButtons() {
        return buttons != null ? buttons : new ArrayList<HitogoButton>();
    }

    public final SparseArray<String> getTextMap() {
        return textMap;
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

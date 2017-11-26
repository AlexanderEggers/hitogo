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
    private String tag;
    private SparseArray<String> textMap;

    private Integer layoutRes;
    private int hashCode;
    private Integer titleViewId;
    private Integer state;

    private List<HitogoButton> buttons;
    private HitogoButton closeButton;

    private HitogoAlertType type;
    private Bundle arguments;
    private HitogoAnimation hitogoAnimation;
    private HitogoVisibilityListener listener;

    @Override
    protected void provideData(HitogoAlertParamsHolder holder, Bundle privateBundle) {
        title = privateBundle.getString("title");
        tag = privateBundle.getString("tag");
        textMap = holder.getTextMap();

        layoutRes = (Integer) privateBundle.getSerializable("layoutRes");
        hashCode = privateBundle.getInt("hashCode");
        titleViewId = (Integer) privateBundle.getSerializable("titleViewId");
        state = (Integer) privateBundle.getSerializable("state");

        buttons = holder.getButtons();
        closeButton = holder.getCloseButton();

        type = (HitogoAlertType) privateBundle.getSerializable("type");
        arguments = privateBundle.getBundle("arguments");
        hitogoAnimation = holder.getAnimation();
        listener = holder.getVisibilityListener();

        onCreateParams(holder);
    }

    protected abstract void onCreateParams(HitogoAlertParamsHolder holder);

    public final String getTitle() {
        return title;
    }

    public final Integer getTitleViewId() {
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

    public final Integer getLayoutRes() {
        return layoutRes;
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

    public final HitogoVisibilityListener getVisibilityListener() {
        return listener;
    }

    public boolean consumeLayoutClick() {
        return false;
    }
}

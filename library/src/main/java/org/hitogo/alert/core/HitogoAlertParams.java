package org.hitogo.alert.core;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.transition.Transition;
import android.util.SparseArray;

import org.hitogo.alert.view.anim.HitogoAnimation;
import org.hitogo.button.core.HitogoButton;
import org.hitogo.core.HitogoParams;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"WeakerAccess", "unused"})
public abstract class HitogoAlertParams extends HitogoParams<HitogoAlertParamsHolder, HitogoAlertParams> {

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
    private HitogoAnimation animation;
    private HitogoVisibilityListener visibilityListener;
    private List<Transition> transitions;
    private List<Object> customObjects;

    @Override
    protected void provideData(HitogoAlertParamsHolder holder, Bundle privateBundle) {
        title = privateBundle.getString(HitogoAlertParamsKeys.TITLE_KEY);
        tag = privateBundle.getString(HitogoAlertParamsKeys.TAG_KEY);
        textMap = holder.getTextMap();

        layoutRes = (Integer) privateBundle.getSerializable(HitogoAlertParamsKeys.LAYOUT_RES_KEY);
        hashCode = privateBundle.getInt(HitogoAlertParamsKeys.HASH_CODE_KEY);
        titleViewId = (Integer) privateBundle.getSerializable(HitogoAlertParamsKeys.TITLE_VIEW_ID_KEY);
        state = (Integer) privateBundle.getSerializable(HitogoAlertParamsKeys.STATE_KEY);

        buttons = holder.getButtons();
        closeButton = holder.getCloseButton();

        type = (HitogoAlertType) privateBundle.getSerializable(HitogoAlertParamsKeys.TYPE_KEY);
        arguments = privateBundle.getBundle(HitogoAlertParamsKeys.ARGUMENTS_KEY);
        animation = holder.getAnimation();
        visibilityListener = holder.getVisibilityListener();
        transitions = holder.getTransitions();
        customObjects = holder.getCustomObjects();

        onCreateParams(holder, this);
    }

    protected abstract void onCreateParams(HitogoAlertParamsHolder holder, HitogoAlertParams alertParams);

    public final String getTitle() {
        return title;
    }

    public final Integer getTitleViewId() {
        return titleViewId;
    }

    public final boolean hasAnimation() {
        return animation != null;
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
        return animation;
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
        return visibilityListener;
    }

    @NonNull
    public final List<Object> getCustomObjects() {
        return customObjects;
    }

    @NonNull
    public final List<Transition> getTransitions() {
        return transitions;
    }

    public boolean consumeLayoutClick() {
        return false;
    }
}

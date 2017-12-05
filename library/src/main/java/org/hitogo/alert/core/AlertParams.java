package org.hitogo.alert.core;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.transition.Transition;
import android.util.SparseArray;
import android.view.View;

import org.hitogo.core.HitogoAnimation;
import org.hitogo.button.core.Button;
import org.hitogo.core.HitogoParams;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"WeakerAccess", "unused"})
public abstract class AlertParams extends HitogoParams<AlertParamsHolder, AlertParams> {

    private String title;
    private String tag;
    private SparseArray<String> textMap;

    private Integer layoutRes;
    private int hashCode;
    private Integer titleViewId;
    private Integer state;

    private List<Button> buttons;
    private Button closeButton;

    private AlertType type;
    private Bundle arguments;
    private HitogoAnimation animation;
    private VisibilityListener visibilityListener;
    private List<Transition> transitions;
    private List<Object> customObjects;
    private View.OnTouchListener onTouchListener;

    @Override
    protected void provideData(AlertParamsHolder holder, Bundle privateBundle) {
        title = privateBundle.getString(AlertParamsKeys.TITLE_KEY);
        tag = privateBundle.getString(AlertParamsKeys.TAG_KEY);
        textMap = holder.getTextMap();

        layoutRes = (Integer) privateBundle.getSerializable(AlertParamsKeys.LAYOUT_RES_KEY);
        hashCode = privateBundle.getInt(AlertParamsKeys.HASH_CODE_KEY);
        titleViewId = (Integer) privateBundle.getSerializable(AlertParamsKeys.TITLE_VIEW_ID_KEY);
        state = (Integer) privateBundle.getSerializable(AlertParamsKeys.STATE_KEY);

        buttons = holder.getButtons();
        closeButton = holder.getCloseButton();

        type = (AlertType) privateBundle.getSerializable(AlertParamsKeys.TYPE_KEY);
        arguments = privateBundle.getBundle(AlertParamsKeys.ARGUMENTS_KEY);
        animation = holder.getAnimation();
        visibilityListener = holder.getVisibilityListener();
        transitions = holder.getTransitions();
        customObjects = holder.getCustomObjects();
        onTouchListener = holder.getOnTouchListener();

        onCreateParams(holder, this);
    }

    protected abstract void onCreateParams(AlertParamsHolder holder, AlertParams alertParams);

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
    public final AlertType getType() {
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
    public final List<Button> getButtons() {
        return buttons != null ? buttons : new ArrayList<Button>();
    }

    public final SparseArray<String> getTextMap() {
        return textMap;
    }

    public final Bundle getArguments() {
        return arguments;
    }

    public final Button getCloseButton() {
        return closeButton;
    }

    public final VisibilityListener getVisibilityListener() {
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

    public final View.OnTouchListener getOnTouchListener() {
        return onTouchListener;
    }

    public boolean consumeLayoutClick() {
        return false;
    }
}

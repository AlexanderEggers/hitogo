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
    private Integer titleViewId;
    private Integer state;

    private List<Button> buttons;
    private Button closeButton;

    private AlertType type;
    private Bundle arguments;
    private HitogoAnimation animation;
    private List<VisibilityListener> visibilityListener;
    private List<Transition> transitions;
    private View.OnTouchListener onTouchListener;

    @Override
    protected void provideData(AlertParamsHolder holder, Bundle privateBundle) {
        title = privateBundle.getString(AlertParamsKeys.TITLE_KEY);
        tag = privateBundle.getString(AlertParamsKeys.TAG_KEY);
        textMap = holder.getTextMap();

        layoutRes = (Integer) privateBundle.getSerializable(AlertParamsKeys.LAYOUT_RES_KEY);
        titleViewId = (Integer) privateBundle.getSerializable(AlertParamsKeys.TITLE_VIEW_ID_KEY);
        state = (Integer) privateBundle.getSerializable(AlertParamsKeys.STATE_KEY);

        buttons = holder.getButtons();
        closeButton = holder.getCloseButton();

        type = (AlertType) privateBundle.getSerializable(AlertParamsKeys.TYPE_KEY);
        arguments = privateBundle.getBundle(AlertParamsKeys.ARGUMENTS_KEY);
        animation = holder.getAnimation();
        visibilityListener = holder.getVisibilityListener();
        transitions = holder.getTransitions();
        onTouchListener = holder.getOnTouchListener();

        onCreateParams(holder, this);
    }

    protected abstract void onCreateParams(AlertParamsHolder holder, AlertParams alertParams);

    public String getTitle() {
        return title;
    }

    public Integer getTitleViewId() {
        return titleViewId;
    }

    public boolean hasAnimation() {
        return animation != null;
    }

    public boolean isClosingOthers() {
        return true;
    }

    @NonNull
    public AlertType getType() {
        return type;
    }

    @NonNull
    public String getTag() {
        return tag;
    }

    public Integer getState() {
        return state;
    }

    public Integer getLayoutRes() {
        return layoutRes;
    }

    @Nullable
    public HitogoAnimation getAnimation() {
        return animation;
    }

    @NonNull
    public List<Button> getButtons() {
        return buttons != null ? buttons : new ArrayList<Button>();
    }

    @NonNull
    public SparseArray<String> getTextMap() {
        return textMap != null ? textMap : new SparseArray<String>();
    }

    public Bundle getArguments() {
        return arguments;
    }

    public Button getCloseButton() {
        return closeButton;
    }

    public List<VisibilityListener> getVisibilityListener() {
        return visibilityListener;
    }

    @NonNull
    public List<Transition> getTransitions() {
        return transitions;
    }

    public View.OnTouchListener getOnTouchListener() {
        return onTouchListener;
    }

    public boolean dismissByLayoutClick() {
        return false;
    }
}

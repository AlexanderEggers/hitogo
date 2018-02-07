package org.hitogo.alert.core;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.SparseArray;

import org.hitogo.button.core.Button;
import org.hitogo.core.HitogoParams;
import org.hitogo.core.HitogoParamsHolder;

import java.util.Collections;
import java.util.List;

/**
 * This class is implemented to provide values to the different alert types. Due to the abstract
 * implementation, each alert type will have it's own AlertParams sub-class. This class provides
 * general values which all alert types are sharing.
 *
 * @since 1.0.0
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public abstract class AlertParams extends HitogoParams<HitogoParamsHolder, AlertParams> {

    private String title;
    private String tag;
    private SparseArray<String> textMap;

    private Integer layoutRes;
    private Integer titleViewId;
    private Integer state;
    private Integer priority;

    private List<Button> buttons;
    private Button closeButton;

    private AlertType type;
    private Bundle arguments;
    private List<VisibilityListener> visibilityListener;

    @Override
    protected void provideData(HitogoParamsHolder holder) {
        title = holder.getString(AlertParamsKeys.TITLE_KEY);
        tag = holder.getString(AlertParamsKeys.TAG_KEY);
        textMap = holder.getCustomObject(AlertParamsKeys.TEXT_KEY);

        layoutRes = (Integer) holder.getSerializable(AlertParamsKeys.LAYOUT_RES_KEY);
        titleViewId = (Integer) holder.getSerializable(AlertParamsKeys.TITLE_VIEW_ID_KEY);
        state = (Integer) holder.getSerializable(AlertParamsKeys.STATE_KEY);
        priority = (Integer) holder.getSerializable(AlertParamsKeys.PRIORITY_KEY);

        buttons = holder.getCustomObject(AlertParamsKeys.BUTTONS_KEY);
        closeButton = holder.getCustomObject(AlertParamsKeys.CLOSE_BUTTON_KEY);

        type = (AlertType) holder.getSerializable(AlertParamsKeys.TYPE_KEY);
        arguments = holder.getBundle(AlertParamsKeys.ARGUMENTS_KEY);

        visibilityListener = holder.getCustomObject(AlertParamsKeys.VISIBILITY_LISTENER_KEY);

        onCreateParams(holder, this);
    }

    public abstract boolean hasAnimation();

    public abstract boolean isClosingOthers();

    public String getTitle() {
        return title;
    }

    public Integer getTitleViewId() {
        return titleViewId;
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

    @NonNull
    public List<Button> getButtons() {
        return buttons != null ? buttons : Collections.<Button>emptyList();
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

    public boolean dismissByLayoutClick() {
        return false;
    }

    public Integer getPriority() {
        return priority;
    }
}

package org.hitogo.view;

import android.os.Bundle;

import org.hitogo.core.HitogoParams;
import org.hitogo.core.HitogoParamsHolder;

@SuppressWarnings({"WeakerAccess", "unused"})
public final class HitogoViewParams extends HitogoParams {

    private boolean showAnimation;
    private boolean isDismissible;
    private Bundle arguments;

    private String title;
    private String text;

    private Integer state;
    private Integer containerId;
    private Integer titleViewId;
    private Integer textViewId;
    private Integer layoutViewId;

    @Override
    protected void onCreateParams(HitogoParamsHolder holder) {
        title = holder.getString("title");
        text = holder.getString("text");
        state = holder.getInteger("state");
        containerId = holder.getInteger("containerId");
        titleViewId = holder.getInteger("titleViewId");
        textViewId = holder.getInteger("textViewId");
        layoutViewId = holder.getInteger("layoutViewId");
        showAnimation = holder.getBoolean("showAnimation");
        isDismissible = holder.getBoolean("isDismissible");
        arguments = holder.getExtras("arguments");
    }

    @Override
    public boolean hasAnimation() {
        return showAnimation;
    }

    protected boolean isDismissible() {
        return isDismissible;
    }

    public Bundle getArguments() {
        return arguments;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public Integer getState() {
        return state;
    }

    public Integer getContainerId() {
        return containerId;
    }

    public Integer getTitleViewId() {
        return titleViewId;
    }

    public Integer getTextViewId() {
        return textViewId;
    }

    public Integer getLayoutViewId() {
        return layoutViewId;
    }
}

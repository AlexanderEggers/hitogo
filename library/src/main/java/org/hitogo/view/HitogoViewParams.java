package org.hitogo.view;

import org.hitogo.core.HitogoParams;
import org.hitogo.core.HitogoParamsHolder;

@SuppressWarnings({"WeakerAccess", "unused"})
public class HitogoViewParams extends HitogoParams {

    private boolean isDismissible;
    private boolean closeOthers;

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
        isDismissible = holder.getBoolean("isDismissible");
        closeOthers = holder.getBoolean("closeOthers");
    }

    protected boolean isDismissible() {
        return isDismissible;
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

    @Override
    public boolean isClosingOthers() {
        return closeOthers;
    }
}

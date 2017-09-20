package org.hitogo.alert.view;

import org.hitogo.alert.core.HitogoAlertParams;
import org.hitogo.alert.core.HitogoAlertParamsHolder;

@SuppressWarnings({"WeakerAccess", "unused"})
public class HitogoViewParams extends HitogoAlertParams {

    private String title;
    private String text;

    private Integer containerId;
    private Integer titleViewId;
    private Integer textViewId;
    private Integer layoutViewId;

    private boolean isDismissible;
    private boolean closeOthers;
    private boolean dismissByClick;

    @Override
    protected void onCreateParams(HitogoAlertParamsHolder holder) {
        title = holder.getString("title");
        text = holder.getString("text");

        containerId = holder.getInteger("containerId");
        titleViewId = holder.getInteger("titleViewId");
        textViewId = holder.getInteger("textViewId");
        layoutViewId = holder.getInteger("layoutViewId");

        isDismissible = holder.getBoolean("isDismissible");
        closeOthers = holder.getBoolean("closeOthers");
        dismissByClick = holder.getBoolean("dismissByClick");
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
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

    protected boolean isDismissible() {
        return isDismissible;
    }

    @Override
    public boolean consumeLayoutClick() {
        return dismissByClick;
    }
}

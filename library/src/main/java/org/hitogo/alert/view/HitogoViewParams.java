package org.hitogo.alert.view;

import org.hitogo.alert.core.HitogoAlertParams;
import org.hitogo.alert.core.HitogoAlertParamsHolder;

@SuppressWarnings({"WeakerAccess", "unused"})
public class HitogoViewParams extends HitogoAlertParams {

    private Integer containerId;
    private Integer layoutViewId;

    private boolean isDismissible;
    private boolean closeOthers;
    private boolean dismissByClick;

    @Override
    protected void onCreateParams(HitogoAlertParamsHolder holder) {
        containerId = holder.getInteger("containerId");
        layoutViewId = holder.getInteger("layoutViewId");

        isDismissible = holder.getBoolean("isDismissible");
        closeOthers = holder.getBoolean("closeOthers");
        dismissByClick = holder.getBoolean("dismissByClick");
    }

    public Integer getContainerId() {
        return containerId;
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

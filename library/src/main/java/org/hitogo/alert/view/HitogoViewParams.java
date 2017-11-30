package org.hitogo.alert.view;

import org.hitogo.alert.core.HitogoAlertParams;
import org.hitogo.alert.core.HitogoAlertParamsHolder;

@SuppressWarnings({"WeakerAccess", "unused"})
public class HitogoViewParams extends HitogoAlertParams {

    private Integer containerId;
    private Integer innerLayoutViewId;

    private boolean isDismissible;
    private boolean closeOthers;
    private boolean dismissByClick;

    @Override
    protected void onCreateParams(HitogoAlertParamsHolder holder, HitogoAlertParams alertParams) {
        containerId = holder.getInteger(HitogoViewParamsKeys.CONTAINER_ID_KEY);
        innerLayoutViewId = holder.getInteger(HitogoViewParamsKeys.INNER_LAYOUT_VIEW_ID_KEY);

        isDismissible = holder.getBoolean(HitogoViewParamsKeys.IS_DISMISSIBLE_KEY);
        closeOthers = holder.getBoolean(HitogoViewParamsKeys.CLOSE_OTHERS_KEY);
        dismissByClick = holder.getBoolean(HitogoViewParamsKeys.DISMISS_BY_CLICK_KEY);
    }

    public Integer getContainerId() {
        return containerId;
    }

    public Integer getInnerLayoutViewId() {
        return innerLayoutViewId;
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

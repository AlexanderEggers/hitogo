package org.hitogo.alert.view;

import org.hitogo.alert.core.AlertParams;
import org.hitogo.alert.core.AlertParamsHolder;

@SuppressWarnings({"WeakerAccess", "unused"})
public class ViewAlertParams extends AlertParams {

    private Integer containerId;
    private Integer innerLayoutViewId;

    private boolean closeOthers;
    private boolean dismissByClick;

    @Override
    protected void onCreateParams(AlertParamsHolder holder, AlertParams alertParams) {
        containerId = holder.getInteger(ViewAlertParamsKeys.CONTAINER_ID_KEY);
        innerLayoutViewId = holder.getInteger(ViewAlertParamsKeys.INNER_LAYOUT_VIEW_ID_KEY);

        closeOthers = holder.getBoolean(ViewAlertParamsKeys.CLOSE_OTHERS_KEY);
        dismissByClick = holder.getBoolean(ViewAlertParamsKeys.DISMISS_BY_LAYOUT_CLICK_KEY);
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

    @Override
    public boolean dismissByLayoutClick() {
        return dismissByClick;
    }
}

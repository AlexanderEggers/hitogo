package org.hitogo.alert.view;

import android.support.annotation.Nullable;

import org.hitogo.alert.core.AlertParams;
import org.hitogo.core.HitogoAnimation;
import org.hitogo.core.HitogoParamsHolder;

@SuppressWarnings({"WeakerAccess", "unused"})
public class ViewAlertParams extends AlertParams {

    private Integer containerId;
    private Integer innerLayoutViewId;

    private boolean closeOthers;
    private boolean dismissByClick;

    private HitogoAnimation animation;

    @Override
    protected void onCreateParams(HitogoParamsHolder holder) {
        containerId = holder.getInteger(ViewAlertParamsKeys.CONTAINER_ID_KEY);
        innerLayoutViewId = holder.getInteger(ViewAlertParamsKeys.INNER_LAYOUT_VIEW_ID_KEY);

        closeOthers = holder.getBoolean(ViewAlertParamsKeys.CLOSE_OTHERS_KEY);
        dismissByClick = holder.getBoolean(ViewAlertParamsKeys.DISMISS_BY_LAYOUT_CLICK_KEY);

        animation = holder.getCustomObject(ViewAlertParamsKeys.ANIMATION_KEY);
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

    public boolean dismissByLayoutClick() {
        return dismissByClick;
    }

    @Nullable
    public HitogoAnimation getAnimation() {
        return animation;
    }

    @Override
    public boolean hasAnimation() {
        return animation != null;
    }
}

package org.hitogo.alert.view;

import android.support.annotation.Nullable;

import org.hitogo.alert.core.AlertParams;
import org.hitogo.core.HitogoAnimation;
import org.hitogo.core.HitogoParamsHolder;

/**
 * Params object for the ViewAlert.
 *
 * @see AlertParams
 * @since 1.0.0
 */
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

    @Override
    public boolean isClosingOthers() {
        return closeOthers;
    }

    @Nullable
    public HitogoAnimation getAnimation() {
        return animation;
    }

    @Override
    public boolean hasAnimation() {
        return animation != null;
    }

    /**
     * Returns the container id for the alert.
     *
     * @return an Integer or null
     * @since 1.0.0
     */
    public Integer getContainerId() {
        return containerId;
    }

    /**
     * Returns the inner layout view id for the alert.
     *
     * @return an Integer or null
     * @since 1.0.0
     */
    public Integer getInnerLayoutViewId() {
        return innerLayoutViewId;
    }

    /**
     * Returns if the alert is dismissed when clicked on it's layout.
     *
     * @return an Integer or null
     * @since 1.0.0
     */
    public boolean dismissByLayoutClick() {
        return dismissByClick;
    }
}

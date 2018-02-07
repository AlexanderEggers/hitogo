package org.hitogo.alert.toast;

import org.hitogo.alert.core.AlertParams;
import org.hitogo.core.HitogoParamsHolder;

public class ToastAlertParams extends AlertParams {

    private int duration;

    private Integer gravity;
    private Integer xOffset;
    private Integer yOffset;

    private Float horizontalMargin;
    private Float verticalMargin;

    @Override
    protected void onCreateParams(HitogoParamsHolder holder, AlertParams alertParams) {
        duration = holder.getInteger(ToastAlertParamsKeys.DURATION_KEY);

        gravity = holder.getInteger(ToastAlertParamsKeys.GRAVITY_KEY);
        xOffset = holder.getInteger(ToastAlertParamsKeys.X_OFFSET_KEY);
        yOffset = holder.getInteger(ToastAlertParamsKeys.Y_OFFSET_KEY);

        horizontalMargin = holder.getFloat(ToastAlertParamsKeys.HORIZONTAL_MARGIN_KEY);
        verticalMargin = holder.getFloat(ToastAlertParamsKeys.VERTICAL_MARGIN_KEY);
    }

    public Integer getGravity() {
        return gravity;
    }

    public Integer getxOffset() {
        return xOffset;
    }

    public Integer getyOffset() {
        return yOffset;
    }

    public int getDuration() {
        return duration;
    }

    public Float getHorizontalMargin() {
        return horizontalMargin;
    }

    public Float getVerticalMargin() {
        return verticalMargin;
    }

    @Override
    public boolean hasAnimation() {
        return false;
    }

    @Override
    public boolean isClosingOthers() {
        return false;
    }
}

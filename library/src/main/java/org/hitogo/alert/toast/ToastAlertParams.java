package org.hitogo.alert.toast;

import org.hitogo.alert.core.AlertParams;
import org.hitogo.core.HitogoParamsHolder;

/**
 * Params object for the ToastAlert.
 *
 * @see AlertParams
 * @since 1.0.0
 */
public class ToastAlertParams extends AlertParams {

    private int duration;

    private Integer gravity;
    private Integer xOffset;
    private Integer yOffset;

    private Float horizontalMargin;
    private Float verticalMargin;

    @Override
    protected void onCreateParams(HitogoParamsHolder holder) {
        duration = holder.getInteger(ToastAlertParamsKeys.DURATION_KEY);

        gravity = holder.getInteger(ToastAlertParamsKeys.GRAVITY_KEY);
        xOffset = holder.getInteger(ToastAlertParamsKeys.X_OFFSET_KEY);
        yOffset = holder.getInteger(ToastAlertParamsKeys.Y_OFFSET_KEY);

        horizontalMargin = holder.getFloat(ToastAlertParamsKeys.HORIZONTAL_MARGIN_KEY);
        verticalMargin = holder.getFloat(ToastAlertParamsKeys.VERTICAL_MARGIN_KEY);
    }

    @Override
    public boolean hasAnimation() {
        return false;
    }

    @Override
    public boolean isClosingOthers() {
        return false;
    }

    /**
     * Returns the gravity for the alert.
     *
     * @return an Integer or null
     * @since 1.0.0
     */
    public Integer getGravity() {
        return gravity;
    }

    /**
     * Returns the xOffset for the alert.
     *
     * @return an Integer or null
     * @since 1.0.0
     */
    public Integer getxOffset() {
        return xOffset;
    }

    /**
     * Returns the yOffset for the alert.
     *
     * @return an Integer or null
     * @since 1.0.0
     */
    public Integer getyOffset() {
        return yOffset;
    }

    /**
     * Returns the duration for the alert.
     *
     * @return an Int
     * @since 1.0.0
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Returns the horizontal margin for the alert.
     *
     * @return an Float or null
     * @since 1.0.0
     */
    public Float getHorizontalMargin() {
        return horizontalMargin;
    }

    /**
     * Returns the vertical margin for the alert.
     *
     * @return an Float or null
     * @since 1.0.0
     */
    public Float getVerticalMargin() {
        return verticalMargin;
    }
}

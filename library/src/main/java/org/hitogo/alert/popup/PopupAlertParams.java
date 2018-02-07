package org.hitogo.alert.popup;

import android.transition.Transition;
import android.view.View;

import org.hitogo.alert.core.AlertParams;
import org.hitogo.core.HitogoParamsHolder;

@SuppressWarnings({"WeakerAccess", "unused"})
public class PopupAlertParams extends AlertParams {

    private Integer drawableRes;
    private Integer anchorViewId;
    private Integer animationStyle;
    private Integer gravity;

    private int xoff;
    private int yoff;
    private int width;
    private int height;

    private Float elevation;
    private String anchorViewTag;
    private boolean isDismissible;

    private Transition enterTransition;
    private Transition exitTransition;
    private View.OnTouchListener onTouchListener;

    @Override
    protected void onCreateParams(HitogoParamsHolder holder, AlertParams alertParams) {
        drawableRes = holder.getInteger(PopupAlertParamsKeys.DRAWABLE_RES_KEY);
        anchorViewId = holder.getInteger(PopupAlertParamsKeys.ANCHOR_VIEW_ID_KEY);
        animationStyle = holder.getInteger(PopupAlertParamsKeys.ANIMATION_STYLE_KEY);
        gravity = holder.getInteger(PopupAlertParamsKeys.GRAVITY_KEY);

        xoff = holder.getInteger(PopupAlertParamsKeys.X_OFF_KEY);
        yoff = holder.getInteger(PopupAlertParamsKeys.Y_OFF_KEY);
        width = holder.getInteger(PopupAlertParamsKeys.WIDTH_KEY);
        height = holder.getInteger(PopupAlertParamsKeys.HEIGHT_KEY);

        elevation = holder.getFloat(PopupAlertParamsKeys.ELEVATION_KEY);
        anchorViewTag = holder.getString(PopupAlertParamsKeys.ANCHOR_VIEW_TAG_KEY);
        isDismissible = holder.getBoolean(PopupAlertParamsKeys.IS_DISMISSIBLE_KEY);

        enterTransition = holder.getCustomObject(PopupAlertParamsKeys.ENTER_TRANSITION_KEY);
        exitTransition = holder.getCustomObject(PopupAlertParamsKeys.EXIT_TRANSITION_KEY);
        onTouchListener = holder.getCustomObject(PopupAlertParamsKeys.TOUCH_LISTENER_KEY);
    }

    public Integer getAnchorViewId() {
        return anchorViewId;
    }

    public int getXoff() {
        return xoff;
    }

    public int getYoff() {
        return yoff;
    }

    public Integer getDrawableRes() {
        return drawableRes;
    }

    public Integer getGravity() {
        return gravity;
    }

    public Integer getAnimationStyle() {
        return animationStyle;
    }

    public Float getElevation() {
        return elevation;
    }

    public String getAnchorViewTag() {
        return anchorViewTag;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    protected boolean isDismissible() {
        return isDismissible;
    }

    public Transition getEnterTransition() {
        return enterTransition;
    }

    public Transition getExitTransition() {
        return exitTransition;
    }

    public View.OnTouchListener getOnTouchListener() {
        return onTouchListener;
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

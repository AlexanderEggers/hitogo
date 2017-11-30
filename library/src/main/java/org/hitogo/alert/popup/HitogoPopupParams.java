package org.hitogo.alert.popup;

import android.transition.Transition;

import org.hitogo.alert.core.HitogoAlertParams;
import org.hitogo.alert.core.HitogoAlertParamsHolder;

import java.util.List;

@SuppressWarnings({"WeakerAccess", "unused"})
public class HitogoPopupParams extends HitogoAlertParams {

    private Integer drawableRes;
    private Integer anchorViewId;
    private Integer animationStyle;

    private int xoff;
    private int yoff;
    private int width;
    private int height;

    private Float elevation;
    private String anchorViewTag;
    private boolean isDismissible;

    private Transition enterTransition;
    private Transition exitTransition;

    @Override
    protected void onCreateParams(HitogoAlertParamsHolder holder, HitogoAlertParams alertParams) {
        drawableRes = holder.getInteger(HitogoPopupParamsKeys.DRAWABLE_RES_KEY);
        anchorViewId = holder.getInteger(HitogoPopupParamsKeys.ANCHOR_VIEW_ID_KEY);
        animationStyle = holder.getInteger(HitogoPopupParamsKeys.ANIMATION_STYLE_KEY);

        xoff = holder.getInteger(HitogoPopupParamsKeys.X_OFF_KEY);
        yoff = holder.getInteger(HitogoPopupParamsKeys.Y_OFF_KEY);
        width = holder.getInteger(HitogoPopupParamsKeys.WIDTH_KEY);
        height = holder.getInteger(HitogoPopupParamsKeys.HEIGHT_KEY);

        elevation = holder.getFloat(HitogoPopupParamsKeys.ELEVATION_KEY);
        anchorViewTag = holder.getString(HitogoPopupParamsKeys.ANCHOR_VIEW_TAG_KEY);
        isDismissible = holder.getBoolean(HitogoPopupParamsKeys.IS_DISMISSIBLE_KEY);

        List<Transition> transitions = alertParams.getTransitions();
        enterTransition = !transitions.isEmpty() ? getTransitions().get(0) : null;
        exitTransition = transitions.size() >= 2 ? getTransitions().get(1) : null;
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
}

package org.hitogo.alert.popup;

import android.transition.Transition;
import android.view.View;

import org.hitogo.alert.core.AlertParams;
import org.hitogo.core.HitogoParamsHolder;

/**
 * Params object for the DialogAlert.
 *
 * @see AlertParams
 * @since 1.0.0
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public class PopupAlertParams extends AlertParams {

    private Integer drawableRes;
    private Integer anchorViewId;
    private Integer gravity;

    private int animationStyle;
    private int xoff;
    private int yoff;
    private int width;
    private int height;

    private Float elevation;
    private String anchorViewTag;
    private boolean isDismissible;
    private boolean dismissByLayoutClick;
    private boolean fullScreen;

    private Transition enterTransition;
    private Transition exitTransition;
    private View.OnTouchListener onTouchListener;

    @Override
    protected void onCreateParams(HitogoParamsHolder holder) {
        drawableRes = holder.getInteger(PopupAlertParamsKeys.DRAWABLE_RES_KEY);
        anchorViewId = holder.getInteger(PopupAlertParamsKeys.ANCHOR_VIEW_ID_KEY);
        animationStyle = holder.getInteger(PopupAlertParamsKeys.ANIMATION_STYLE_KEY);
        gravity = holder.getInteger(PopupAlertParamsKeys.GRAVITY_KEY);

        xoff = holder.getInteger(PopupAlertParamsKeys.X_OFFSET_KEY);
        yoff = holder.getInteger(PopupAlertParamsKeys.Y_OFFSET_KEY);
        width = holder.getInteger(PopupAlertParamsKeys.WIDTH_KEY);
        height = holder.getInteger(PopupAlertParamsKeys.HEIGHT_KEY);

        elevation = holder.getFloat(PopupAlertParamsKeys.ELEVATION_KEY);
        anchorViewTag = holder.getString(PopupAlertParamsKeys.ANCHOR_VIEW_TAG_KEY);
        isDismissible = holder.getBoolean(PopupAlertParamsKeys.IS_DISMISSIBLE_KEY);
        dismissByLayoutClick = holder.getBoolean(PopupAlertParamsKeys.DISMISS_BY_LAYOUT_CLICK_KEY);
        fullScreen = holder.getBoolean(PopupAlertParamsKeys.FULLSCREEN_KEY);

        enterTransition = holder.getCustomObject(PopupAlertParamsKeys.ENTER_TRANSITION_KEY);
        exitTransition = holder.getCustomObject(PopupAlertParamsKeys.EXIT_TRANSITION_KEY);
        onTouchListener = holder.getCustomObject(PopupAlertParamsKeys.TOUCH_LISTENER_KEY);
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
     * Returns the anchor view id for the alert.
     *
     * @return an Integer or null
     * @since 1.0.0
     */
    public Integer getAnchorViewId() {
        return anchorViewId;
    }

    /**
     * Returns the xOffset for the alert.
     *
     * @return absolute horizontal offset from the left of the anchor
     * @since 1.0.0
     */
    public int getXOffset() {
        return xoff;
    }

    /**
     * Returns the yOffset for the alert.
     *
     * @return absolute vertical offset from the top of the anchor
     * @since 1.0.0
     */
    public int getYOffset() {
        return yoff;
    }

    /**
     * Returns the drawable resource for the alert.
     *
     * @return an Integer or null
     * @since 1.0.0
     */
    public Integer getDrawableRes() {
        return drawableRes;
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
     * Returns the animation style when the alert is displayed/dismissed.
     *
     * @return an Int
     * @since 1.0.0
     */
    public int getAnimationStyle() {
        return animationStyle;
    }

    /**
     * Returns the elevation for the alert.
     *
     * @return an Float object or null
     * @since 1.0.0
     */
    public Float getElevation() {
        return elevation;
    }

    /**
     * Returns the anchor view tag for the alert.
     *
     * @return a String or null
     * @since 1.0.0
     */
    public String getAnchorViewTag() {
        return anchorViewTag;
    }

    /**
     * Returns the width for the alert.
     *
     * @return an Int
     * @since 1.0.0
     */
    public int getWidth() {
        return width;
    }

    /**
     * Returns the height for the alert.
     *
     * @return an Int
     * @since 1.0.0
     */
    public int getHeight() {
        return height;
    }

    /**
     * Specifies if the alert is dismissible.
     *
     * @return True if the alert is dismissible, false otherwise
     * @since 1.0.0
     */
    protected boolean isDismissible() {
        return isDismissible;
    }

    /**
     * Specifies if the alert should be dismissed by clicking on the layout.
     *
     * @return True if the alert should be dismissed when the layout is clicked, false otherwise
     * @since 1.0.0
     */
    public boolean isDismissByLayoutClick() {
        return dismissByLayoutClick;
    }

    /**
     * Returns the enter transition for the alert.
     *
     * @return a Transition object or null
     * @since 1.0.0
     */
    public Transition getEnterTransition() {
        return enterTransition;
    }

    /**
     * Returns the exit transition for the alert.
     *
     * @return a Transition object or null
     * @since 1.0.0
     */
    public Transition getExitTransition() {
        return exitTransition;
    }

    /**
     * Returns the onTouchListener for the alert.
     *
     * @return a OnTouchListener or null
     * @since 1.0.0
     */
    public View.OnTouchListener getOnTouchListener() {
        return onTouchListener;
    }

    /**
     * Specifies if the alert should be displayed in fullscreen.
     *
     * @return True if the alert should be displayed in fullscreen, false otherwise
     * @since 1.0.0
     */
    public boolean isFullScreen() {
        return fullScreen;
    }
}

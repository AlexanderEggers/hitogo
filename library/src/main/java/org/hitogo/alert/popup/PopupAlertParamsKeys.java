package org.hitogo.alert.popup;

import org.hitogo.alert.core.AlertParamsKeys;

/**
 * Defines the params keys for the HitogoParamsHolder that will be used by the PopupAlert.
 *
 * @since 1.0.0
 */
public abstract class PopupAlertParamsKeys extends AlertParamsKeys {
    public static final String DRAWABLE_RES_KEY = "drawableRes";
    public static final String ANIMATION_STYLE_KEY = "animationStyle";
    public static final String ANCHOR_VIEW_ID_KEY = "anchorViewId";
    public static final String GRAVITY_KEY = "gravity";
    public static final String X_OFFSET_KEY = "xOffset";
    public static final String Y_OFFSET_KEY = "yOffset";
    public static final String WIDTH_KEY = "width";
    public static final String HEIGHT_KEY = "height";
    public static final String ELEVATION_KEY = "elevation";
    public static final String ANCHOR_VIEW_TAG_KEY = "anchorViewTag";
    public static final String DISMISS_BY_LAYOUT_CLICK_KEY = "dismissByLayoutClick";
    public static final String FULLSCREEN_KEY = "fullscreen";

    public static final String ENTER_TRANSITION_KEY = "enterTransition";
    public static final String EXIT_TRANSITION_KEY = "exitTransition";
    public static final String TOUCH_LISTENER_KEY = "touchListener";
}

package org.hitogo.alert.core;

import org.hitogo.core.HitogoParamsKeys;

/**
 * This class is only used to define common keys for the HitogoParamsHolder which can be used by
 * the AlertParams implementation.
 *
 * @see org.hitogo.core.HitogoParamsHolder
 * @see AlertParams
 * @since 1.0.0
 */
@SuppressWarnings("WeakerAccess")
public abstract class AlertParamsKeys extends HitogoParamsKeys {
    public static final String TITLE_KEY = "title";
    public static final String TITLE_VIEW_ID_KEY = "titleViewId";
    public static final String TAG_KEY = "tag";
    public static final String ARGUMENTS_KEY = "arguments";
    public static final String STATE_KEY = "state";
    public static final String LAYOUT_RES_KEY = "layoutRes";
    public static final String IS_DISMISSIBLE_KEY = "isDismissible";
    public static final String PRIORITY_KEY = "priority";

    public static final String VISIBILITY_LISTENER_KEY = "visibilityListener";
    public static final String TEXT_KEY = "text";
    public static final String DRAWABLE_KEY = "drawable";
    public static final String BUTTONS_KEY = "buttons";
    public static final String CLOSE_BUTTON_KEY = "closeButton";
}

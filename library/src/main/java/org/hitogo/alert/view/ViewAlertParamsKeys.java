package org.hitogo.alert.view;

import org.hitogo.alert.core.AlertParamsKeys;

/**
 * Defines the params keys for the HitogoParamsHolder that will be used by the ViewAlert.
 *
 * @since 1.0.0
 */
public abstract class ViewAlertParamsKeys extends AlertParamsKeys {
    public static final String CONTAINER_ID_KEY = "containerId";
    public static final String INNER_LAYOUT_VIEW_ID_KEY = "innerLayoutViewId";
    public static final String CLOSE_OTHERS_KEY = "closeOthers";
    public static final String DISMISS_BY_LAYOUT_CLICK_KEY = "dismissByLayoutClick";
    public static final String ANIMATION_KEY = "animation";
}

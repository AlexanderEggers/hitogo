package org.hitogo.button.core;

import org.hitogo.core.HitogoParamsKeys;

/**
 * This class is only used to define common keys for the HitogoParamsHolder which can be used by
 * the ButtonParams implementation.
 *
 * @see org.hitogo.core.HitogoParamsHolder
 * @see ButtonParams
 * @since 1.0.0
 */
public abstract class ButtonParamsKeys extends HitogoParamsKeys {
    public static final String CLOSE_AFTER_CLICK_KEY = "closeAfterClick";
    public static final String BUTTON_TYPE_KEY = "buttonType";

    public static final String TEXT_KEY = "text";
    public static final String DRAWABLE_KEY = "drawable";
    public static final String BUTTON_LISTENER_KEY = "buttonListener";
    public static final String BUTTON_PARAMETER_KEY = "buttonParameter";
}

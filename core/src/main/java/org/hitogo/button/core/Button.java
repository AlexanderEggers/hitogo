package org.hitogo.button.core;

/**
 * Public api interface for the basic button object. This interface includes the most basic button
 * methods which is provided by the builder classes.
 *
 * @param <T> Type of the ButtonParams object that the alert is using.
 * @see ButtonParams
 * @see ButtonImpl
 * @since 1.0.0
 */
public interface Button<T extends ButtonParams> {

    /**
     * Returns the button params object. The params object is storing all values for the button.
     *
     * @return an object which is extending ButtonParams
     * @since 1.0.0
     */
    T getParams();

    /**
     * Returns the button type. The types are based on the enum ButtonType which includes VIEW,
     * TEXT and OTHER.
     *
     * @return Type for this alert
     * @see ButtonType
     * @since 1.0.0
     */
    ButtonType getButtonType();
}

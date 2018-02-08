package org.hitogo.alert.core;

/**
 * This enum defines the internal builder and alert system. These values are used to determine
 * the needed lifecycle methods and default values.
 *
 * @see Alert
 * @see AlertLifecycle
 * @since 1.0.0
 */
public enum AlertType {
    VIEW, DIALOG, POPUP, OTHER
}

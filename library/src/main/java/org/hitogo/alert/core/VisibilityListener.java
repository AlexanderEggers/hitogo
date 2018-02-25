package org.hitogo.alert.core;

/**
 * This class is used to inject certain events to the alert lifecycle. This can be used for
 * debugger, tracker or any other operation.
 *
 * @param <T> Class type which is using this VisibilityListener.
 * @see Alert
 * @see AlertBuilderImpl
 * @since 1.0.0
 */
public abstract class VisibilityListener<T extends Alert> {

    /**
     * Called when the alert has executed the method "build" provided by the builder system.
     * The alert is not visible but the calculation for display-process has been executed.
     *
     * @param object an object which is extending Alert
     * @see Alert
     * @see AlertBuilderImpl
     * @since 1.0.0
     */
    public void onCreate(T object) {

    }

    /**
     * Called when the alert has executed the method "makeVisible" provided by the alert object.
     *
     * @param object an object which is extending Alert
     * @see Alert
     * @see AlertBuilderImpl
     * @since 1.0.0
     */
    public void onShow(T object) {

    }

    /**
     * Called when the alert has executed the method "makeInvisible" provided by the alert object.
     *
     * @param object an object which is extending Alert
     * @see Alert
     * @see AlertBuilderImpl
     * @since 1.0.0
     */
    public void onClose(T object) {

    }
}

package org.hitogo.alert.core;

import android.os.Bundle;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.hitogo.button.core.Button;
import org.hitogo.core.HitogoController;

public interface AlertBuilderBase<B extends AlertBuilderBase, A extends Alert> {

    /**
     * Creates the requested alert using the provided builder values. This method is using java
     * reflection to determine the class for the alert and it's alert params object.
     *
     * @return Requested alert object.
     * @see Alert
     * @see AlertParams
     * @since 1.0.0
     */
    @NonNull
    A build();

    /**
     * Binds a different HitogoController object to this alert. This will result in using a
     * different root-view.
     *
     * @param controller New HitogoController object which should replace the current.
     * @return Builder object which has called this method.
     * @see HitogoController
     * @since 1.0.0
     */
    @NonNull
    B setController(@Nullable HitogoController controller);

    /**
     * Adds a bundle object to this alert which can be used inside the alert implementation. This
     * method makes only sense for custom alert implementation which are certain bundle objects.
     *
     * @param arguments Bundle object for the alert
     * @return Builder object which has called this method.
     * @see Bundle
     * @since 1.0.0
     */
    @NonNull
    B setBundle(@NonNull Bundle arguments);

    /**
     * Adds a tag to this alert, which makes it closable by using the closeByTag from the
     * HitogoController. This tag is one way to make the alert unique.
     *
     * @param tag Tag for the alert object.
     * @return Builder object which has called this method.
     * @see HitogoController
     * @since 1.0.0
     */
    @NonNull
    B setTag(@NonNull String tag);

    /**
     * Sets the state for this alert. The state can define different areas of the alert, like
     * closeByState or provide(...)Layout using the HitogoController. Usually this method should be
     * called if the alert can use more than one layout or visual state.
     *
     * @param state State for the alert object.
     * @return Builder object which has called this method.
     * @see HitogoController
     * @since 1.0.0
     */
    @NonNull
    B setState(@Nullable Integer state);

    /**
     * Sets the state for this alert. The state can define different areas of the alert, like
     * closeByState or provide(...)Layout using the HitogoController. Usually this method should be
     * called if the alert can use more than one layout or visual state.
     *
     * @param state State for the alert object.
     * @return Builder object which has called this method.
     * @see HitogoController
     * @since 1.0.0
     */
    @NonNull
    B setState(@NonNull Enum state);

    /**
     * Adds a VisibilityListener to the alert. The VisibilityListener can be used to keep track
     * of the different alert states (onCreate, onShow, onClose). Each alerts has the ability to
     * accept more than one VisibilityListener.
     *
     * @param listener VisibilityListener for the alert.
     * @return Builder object which has called this method.
     * @see VisibilityListener
     * @since 1.0.0
     */
    @NonNull
    B addVisibilityListener(@NonNull VisibilityListener<A> listener);

    /**
     * Adds a button to the alert. Buttons are a abstract container for all needed information
     * that one button could have (title, listener, view id, ...).
     *
     * @param buttons One or more button/s for the alert.
     * @return Builder object which has called this method.
     * @see Button
     * @since 1.0.0
     */
    @NonNull
    B addButton(@NonNull Button... buttons);

    /**
     * Sets the priority for the alert. The lower the priority value, the higher it's importance to
     * the system. This value is used to determine the "importance" of a certain alert. Alerts with
     * a lower importance will always be closed if a new higher priority is about to get visible.
     * The new alert will only be displayed if no other alert with the same or lower priority have
     * been found. If closing a prioritized alert, you can use showNext() (HitogoController) to
     * display the next "important" alert.<br>
     * <br>
     * <b>IMPORTANT: If no priority is set, the non-priority alert will <u>always</u> be shown even
     * if the current alert has one (unless it's suppressed in another way, like same same
     * content).</b>
     *
     * @param priority Priority for the alert.
     * @return Builder object which has called this method.
     * @see HitogoController
     * @since 1.0.0
     */
    B setPriority(@IntRange(from = 0) int priority);

    /**
     * Builds and displays the alert object.
     */
    void show();
}

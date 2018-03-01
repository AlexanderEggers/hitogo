package org.hitogo.alert.toast;

import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.hitogo.alert.core.Alert;
import org.hitogo.alert.core.AlertBuilder;
import org.hitogo.button.core.Button;

/**
 * Public api interface for the ToastAlertBuilderImpl. This interface includes all methods that
 * can be used by this builder.
 *
 * @see Alert
 * @since 1.0.0
 */
public interface ToastAlertBuilder extends AlertBuilder<ToastAlertBuilder, ToastAlert> {

    /**
     * Marks the alert as closeable by clicking the given button object that is attached to a view
     * element.
     *
     * @param closeButton Button object or null
     * @return Builder object which has called this method.
     * @since 1.0.0
     */
    @NonNull
    ToastAlertBuilder asDismissible(@Nullable Button closeButton);

    /**
     * Sets the gravity and offset for the alert. The gravity value needs to be used from the
     * Gravity class, like Gravity.CENTER. The offset can be defined by the x and y coordinate.
     *
     * @param gravity alignment of the popup relative to the anchor
     * @param xOffset absolute horizontal offset
     * @param yOffset absolute vertical offset
     * @return Builder object which has called this method.
     * @see android.view.Gravity
     * @since 1.0.0
     */
    @NonNull
    ToastAlertBuilder setGravity(@IntRange(from = 0) int gravity, int xOffset, int yOffset);

    /**
     * Specifies the duration for the toast alert.
     *
     * @param duration an Int
     * @return Builder object which has called this method.
     * @since 1.0.0
     */
    @NonNull
    ToastAlertBuilder setDuration(@IntRange(from = 0, to = 1) int duration);

    /**
     * Specifies the margins for the toast alert.
     *
     * @param horizontalMargin a float
     * @param verticalMargin   a float
     * @return Builder object which has called this method.
     * @since 1.0.0
     */
    @NonNull
    ToastAlertBuilder setMargins(float horizontalMargin, float verticalMargin);
}

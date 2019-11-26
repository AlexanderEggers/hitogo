package org.hitogo.alert.popup;

import androidx.annotation.DrawableRes;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.StyleRes;
import android.transition.Transition;
import android.view.View;

import org.hitogo.alert.core.Alert;
import org.hitogo.alert.core.AlertBuilder;
import org.hitogo.button.core.Button;

import static android.os.Build.VERSION_CODES.KITKAT;
import static android.os.Build.VERSION_CODES.LOLLIPOP;
import static android.os.Build.VERSION_CODES.M;

/**
 * Public api interface for the PopupAlertBuilderImpl. This interface includes all methods that can
 * be used by this builder.
 *
 * @see Alert
 * @since 1.0.0
 */
public interface PopupAlertBuilder extends AlertBuilder<PopupAlertBuilder, PopupAlert> {

    /**
     * Marks the alert as dismissible/closeable by clicking outside of the popup.
     *
     * @return Builder object which has called this method.
     * @since 1.0.0
     */
    @NonNull
    PopupAlertBuilder asDismissible();

    /**
     * Marks the alert as dismissible/closeable by clicking outside of the popup.
     *
     * @param isDismissible a boolean
     * @return Builder object which has called this method.
     * @since 1.0.0
     */
    @NonNull
    PopupAlertBuilder asDismissible(boolean isDismissible);

    /**
     * Marks the alert as dismissible/closeable by clicking outside of the popup or the given
     * close button object that is attached to a view element.
     *
     * @param closeButton Button object or null
     * @return Builder object which has called this method.
     * @since 1.0.0
     */
    @NonNull
    PopupAlertBuilder asDismissible(@Nullable Button closeButton);

    /**
     * Sets the anchor view id for the alert. The popup will be attached to this view when
     * displayed.
     *
     * @param anchorViewId an Int value
     * @return Builder object which has called this method.
     * @since 1.0.0
     */
    @NonNull
    PopupAlertBuilder setAnchor(@IdRes int anchorViewId);

    /**
     * Sets the anchor view tag for the alert. The popup will be attached to this view when
     * displayed.
     *
     * @param anchorViewTag a String
     * @return Builder object which has called this method.
     * @since 1.0.0
     */
    @NonNull
    PopupAlertBuilder setAnchor(@NonNull String anchorViewTag);

    /**
     * Sets the offset for the alert. The offset can be defined by the x and y coordinate.
     *
     * @param xOffset absolute horizontal offset from the left of the anchor
     * @param yOffset absolute vertical offset from the top of the anchor
     * @return Builder object which has called this method.
     * @since 1.0.0
     */
    @NonNull
    PopupAlertBuilder setOffset(int xOffset, int yOffset);

    /**
     * Sets the gravity for the alert. The gravity value needs to be used from the Gravity class,
     * like Gravity.CENTER.
     *
     * @param gravity Alignment of the popup relative to the anchor
     * @return Builder object which has called this method.
     * @see android.view.Gravity
     * @since 1.0.0
     */
    @NonNull
    @RequiresApi(KITKAT)
    PopupAlertBuilder setGravity(int gravity);

    /**
     * Specifies the elevation for this popup alert.
     *
     * @param elevation elevation in pixels
     * @return Builder object which has called this method.
     * @since 1.0.0
     */
    @NonNull
    @RequiresApi(LOLLIPOP)
    PopupAlertBuilder setElevation(float elevation);

    /**
     * Specifies the background drawable for the popup alert.
     *
     * @param drawableRes an Integer or null
     * @return Builder object which has called this method.
     * @since 1.0.0
     */
    @NonNull
    PopupAlertBuilder setBackgroundDrawable(@DrawableRes Integer drawableRes);

    /**
     * Specifies the size for the popup alert. The size can be defines by using
     * ViewGroup.LayoutParams.(WRAP_CONTENT/MATCH_PARENT) or by using any value which represents the
     * dp width and height.
     *
     * @param width  an Int which defines the width for the alert
     * @param height an Int which defines the height for the alert
     * @return Builder object which has called this method.
     * @since 1.0.0
     */
    @NonNull
    PopupAlertBuilder setSize(int width, int height);

    /**
     * Specifies the animation style for the alert when the popup appears and disappears.
     *
     * @param animationStyle animation style to use when the popup appears and disappears. Set to
     *                       -1 for the default animation, 0 for no animation, or a resource
     *                       identifier for an explicit animation.
     * @return Builder object which has called this method.
     * @since 1.0.0
     */
    @NonNull
    PopupAlertBuilder setAnimationStyle(@StyleRes int animationStyle);

    /**
     * Sets an onTouchListener for the popup alert to catch all touch events being dispatched to
     * the popup.
     *
     * @param onTouchListener an onTouchListener object
     * @return Builder object which has called this method.
     * @since 1.0.0
     */
    @NonNull
    PopupAlertBuilder setTouchListener(@NonNull View.OnTouchListener onTouchListener);

    /**
     * Sets the enter/exit transition to be used when the popup window is shown/dismissed.
     *
     * @param enterTransition the enter transition, or {@code null} if not set
     * @param exitTransition  the exit transition, or {@code null} if not set
     * @return Builder object which has called this method.
     * @since 1.0.0
     */
    @NonNull
    @RequiresApi(M)
    PopupAlertBuilder setTransition(@Nullable Transition enterTransition, @Nullable Transition exitTransition);

    /**
     * Determines if the alert can be dismissed by clicking on the alert root view.
     *
     * @param dismissByClick True if the alert should be dismissed by click on the layout, false
     *                       otherwise
     * @return Builder object which has called this method.
     * @since 1.0.0
     */
    @NonNull
    PopupAlertBuilder dismissByLayoutClick(boolean dismissByClick);

    /**
     * Specifies if the alert should be shown in fullscreen mode. The fullscreen mode will override
     * any values set by the setSize(int, int) method.
     *
     * @param isFullscreen True if the alert should be displayed in fullscreen mode, false
     *                     otherwise
     * @return Builder object which has called this method.
     * @since 1.0.0
     */
    @NonNull
    PopupAlertBuilder asFullscreen(boolean isFullscreen);

    /**
     * Prepares the show-process for this alert. Depending on the input, the alert will be made
     * visible or stay invisible for later.
     *
     * @param showLater Determines if the alert should be displayed later.
     * @since 1.0.0
     */
    void showLater(boolean showLater);

    /**
     * Delays the show-process for this alert. The delay is depending the input.
     *
     * @param millis Delay in milliseconds.
     * @since 1.0.0
     */
    void showDelayed(long millis);
}

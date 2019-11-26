package org.hitogo.alert.dialog;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.annotation.StyleRes;

import org.hitogo.alert.core.Alert;
import org.hitogo.alert.core.AlertBuilder;
import org.hitogo.button.core.Button;

/**
 * Public api interface for the DialogAlertBuilderImpl. This interface includes all methods that
 * can be used by this builder.
 *
 * @see Alert
 * @since 1.0.0
 */
public interface DialogAlertBuilder extends AlertBuilder<DialogAlertBuilder, DialogAlert> {

    /**
     * Marks the alert as dismissible/closeable by clicking outside of the dialog.
     *
     * @return Builder object which has called this method.
     * @since 1.0.0
     */
    @NonNull
    DialogAlertBuilder asDismissible();

    /**
     * Marks the alert as dismissible/closeable by clicking outside of the dialog.
     *
     * @param isDismissible a boolean
     * @return Builder object which has called this method.
     * @since 1.0.0
     */
    @NonNull
    DialogAlertBuilder asDismissible(boolean isDismissible);

    /**
     * Marks the alert as dismissible/closeable by clicking outside of the dialog or the given
     * close button object that is attached to a view element.
     *
     * @param closeButton Button object or null
     * @return Builder object which has called this method.
     * @since 1.0.0
     */
    @NonNull
    DialogAlertBuilder asDismissible(@Nullable Button closeButton);

    /**
     * Adds one or more button(s) to the alert. Buttons are an abstract container for all needed
     * information that one button could have (title, listener, view id, ...). Using this method
     * buttons are only defined by the given String. This String stands for the button text. The
     * button is defined to close the dialog when clicked.
     *
     * @param buttonContent One or more button/s for the alert.
     * @return Builder object which has called this method.
     * @see Button
     * @since 1.0.0
     */
    @NonNull
    DialogAlertBuilder addButton(@NonNull String... buttonContent);

    /**
     * Adds one or more button(s) to the alert. Buttons are an abstract container for all needed
     * information that one button could have (title, listener, view id, ...). Using this method
     * buttons are only defined by the given Integer. This Integer stands for the button text. The
     * button is defined to close the dialog when clicked.
     *
     * @param buttonContent One or more button/s for the alert.
     * @return Builder object which has called this method.
     * @see Button
     * @since 1.0.0
     */
    @NonNull
    DialogAlertBuilder addButton(@StringRes int... buttonContent);

    /**
     * Sets the style for this dialog alert.
     *
     * @param dialogThemeResId an Int which defines the style
     * @return Builder object which has called this method.
     * @since 1.0.0
     */
    @NonNull
    DialogAlertBuilder setStyle(@StyleRes int dialogThemeResId);
}

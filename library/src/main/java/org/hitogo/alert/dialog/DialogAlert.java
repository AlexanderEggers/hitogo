package org.hitogo.alert.dialog;

import android.app.Dialog;
import android.support.annotation.Nullable;

import org.hitogo.alert.core.Alert;
import org.hitogo.alert.core.AlertImpl;
import org.hitogo.alert.core.AlertParams;

/**
 * Public api interface for the basic dialog alert object. This interface includes the most basic
 * dialog alert methods which is provided by the builder classes.
 *
 * @see AlertParams
 * @see AlertImpl
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public interface DialogAlert extends Alert<DialogAlertParams> {

    /**
     * Returns the dialog of the alert.
     *
     * @return Dialog of the alert. Null if the alert is not using the dialog object.
     * @since 1.0.0
     */
    @Nullable
    Dialog getDialog();
}

package org.hitogo.alert.dialog;

import org.hitogo.alert.core.AlertParams;
import org.hitogo.core.HitogoParamsHolder;

/**
 * Params object for the DialogAlert.
 *
 * @see AlertParams
 * @since 1.0.0
 */
public class DialogAlertParams extends AlertParams {

    private Integer dialogThemeResId;
    private boolean isDismissible;

    @Override
    protected void onCreateParams(HitogoParamsHolder holder) {
        dialogThemeResId = holder.getInteger(DialogAlertParamsKeys.DIALOG_THEME_RES_ID);
        isDismissible = holder.getBoolean(DialogAlertParamsKeys.IS_DISMISSIBLE_KEY);
    }

    /**
     * Returns the dialog theme resource id for the alert.
     *
     * @return an Integer or null
     */
    public Integer getDialogThemeResId() {
        return dialogThemeResId;
    }

    /**
     * Returns if the alert can be dismissed.
     *
     * @return True if the alert can be dismissed, false otherwise.
     */
    public boolean isDismissible() {
        return isDismissible;
    }

    @Override
    public boolean hasAnimation() {
        return false;
    }

    @Override
    public boolean isClosingOthers() {
        return false;
    }
}

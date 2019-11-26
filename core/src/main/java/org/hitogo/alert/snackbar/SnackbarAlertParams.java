package org.hitogo.alert.snackbar;

import android.content.res.ColorStateList;
import com.google.android.material.snackbar.Snackbar;

import org.hitogo.alert.core.AlertParams;
import org.hitogo.core.HitogoParamsHolder;

/**
 * Params object for the SnackbarAlert.
 *
 * @see AlertParams
 * @since 1.0.0
 */
public class SnackbarAlertParams extends AlertParams {

    private Integer actionTextColor;
    private int duration = Snackbar.LENGTH_SHORT;
    private ColorStateList colorStates;

    @Override
    protected void onCreateParams(HitogoParamsHolder holder) {
        actionTextColor = holder.getInteger(SnackbarAlertParamsKeys.ACTION_TEXT_COLOR_KEY);
        duration = holder.getInteger(SnackbarAlertParamsKeys.DURATION_KEY);
        colorStates = holder.getCustomObject(SnackbarAlertParamsKeys.COLOR_STATE_LIST_KEY);
    }

    @Override
    public boolean hasAnimation() {
        return false;
    }

    @Override
    public boolean isClosingOthers() {
        return false;
    }

    /**
     * Returns the action text color for the alert.
     *
     * @return an Integer or null
     * @since 1.0.0
     */
    public Integer getActionTextColor() {
        return actionTextColor;
    }

    /**
     * Returns the duration for the alert.
     *
     * @return an Integer or null
     * @since 1.0.0
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Returns the color state list for the alert.
     *
     * @return an Integer or null
     * @since 1.0.0
     */
    public ColorStateList getColorStates() {
        return colorStates;
    }
}

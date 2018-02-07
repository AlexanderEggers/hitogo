package org.hitogo.alert.snackbar;

import android.content.res.ColorStateList;
import android.support.design.widget.Snackbar;

import org.hitogo.alert.core.AlertParams;
import org.hitogo.core.HitogoParamsHolder;

public class SnackbarAlertParams extends AlertParams {

    private Integer actionTextColor;
    private int duration = Snackbar.LENGTH_SHORT;

    private Snackbar.Callback snackbarCallback;
    private ColorStateList colorStates;

    @Override
    protected void onCreateParams(HitogoParamsHolder holder, AlertParams alertParams) {
        actionTextColor = holder.getInteger(SnackbarAlertParamsKeys.ACTION_TEXT_COLOR_KEY);
        duration = holder.getInteger(SnackbarAlertParamsKeys.DURATION_KEY);

        snackbarCallback = (Snackbar.Callback) holder.getCustomObject(SnackbarAlertParamsKeys.CALLBACK_KEY);
        colorStates = (ColorStateList) holder.getCustomObject(SnackbarAlertParamsKeys.COLOR_STATE_LIST_KEY);
    }

    public Integer getActionTextColor() {
        return actionTextColor;
    }

    public int getDuration() {
        return duration;
    }

    public Snackbar.Callback getSnackbarCallback() {
        return snackbarCallback;
    }

    public ColorStateList getColorStates() {
        return colorStates;
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

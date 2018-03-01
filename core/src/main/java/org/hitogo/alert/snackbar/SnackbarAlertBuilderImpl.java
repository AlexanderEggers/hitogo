package org.hitogo.alert.snackbar;

import android.content.res.ColorStateList;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;

import org.hitogo.alert.core.Alert;
import org.hitogo.alert.core.AlertBuilderImpl;
import org.hitogo.alert.core.AlertImpl;
import org.hitogo.alert.core.AlertParams;
import org.hitogo.alert.core.AlertType;
import org.hitogo.button.core.Button;
import org.hitogo.core.HitogoContainer;
import org.hitogo.core.HitogoController;
import org.hitogo.core.HitogoParamsHolder;

import static org.hitogo.alert.snackbar.SnackbarAlertParamsKeys.ACTION_TEXT_COLOR_KEY;
import static org.hitogo.alert.snackbar.SnackbarAlertParamsKeys.COLOR_STATE_LIST_KEY;
import static org.hitogo.alert.snackbar.SnackbarAlertParamsKeys.DURATION_KEY;

/**
 * Builder which includes all basic method to assign specific snackbar values to the alert.
 *
 * @see Alert
 * @since 1.0.0
 */
public class SnackbarAlertBuilderImpl extends AlertBuilderImpl<SnackbarAlertBuilder, SnackbarAlert> implements SnackbarAlertBuilder {

    private Integer actionTextColor;
    private int duration = Snackbar.LENGTH_SHORT;

    private ColorStateList colorStates;

    /**
     * Default constructor for the SnackbarAlertBuilderImpl.
     *
     * @param targetClass Class object for the requested alert.
     * @param paramClass  Class object for the params object which is used by the alert.
     * @param container   Container which is used as a reference for this alert (context, view,
     *                    controller).
     * @see HitogoContainer
     * @see HitogoController
     * @see AlertType
     * @since 1.0.0
     */
    public SnackbarAlertBuilderImpl(@NonNull Class<? extends AlertImpl> targetClass,
                                    @NonNull Class<? extends AlertParams> paramClass,
                                    @NonNull HitogoContainer container) {
        super(targetClass, paramClass, container, AlertType.OTHER);
    }

    @NonNull
    @Override
    public SnackbarAlertBuilder setAction(@NonNull Button button) {
        return super.addButton(button);
    }

    @NonNull
    @Override
    public SnackbarAlertBuilder setActionTextColor(int color) {
        this.actionTextColor = color;
        this.colorStates = null;
        return this;
    }

    @NonNull
    @Override
    public SnackbarAlertBuilder setActionTextColor(@NonNull ColorStateList colors) {
        this.actionTextColor = null;
        this.colorStates = colors;
        return this;
    }

    @NonNull
    @Override
    public SnackbarAlertBuilder setDuration(int duration) {
        this.duration = duration;
        return this;
    }

    @Override
    protected void onProvideData(HitogoParamsHolder holder) {
        super.onProvideData(holder);

        holder.provideInteger(ACTION_TEXT_COLOR_KEY, actionTextColor);
        holder.provideInteger(DURATION_KEY, duration);
        holder.provideCustomObject(COLOR_STATE_LIST_KEY, colorStates);
    }
}

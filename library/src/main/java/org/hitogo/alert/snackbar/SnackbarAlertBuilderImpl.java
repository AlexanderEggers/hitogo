package org.hitogo.alert.snackbar;

import android.content.res.ColorStateList;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;

import org.hitogo.alert.core.AlertBuilderImpl;
import org.hitogo.alert.core.AlertImpl;
import org.hitogo.alert.core.AlertParams;
import org.hitogo.alert.core.AlertParamsHolder;
import org.hitogo.alert.core.AlertType;
import org.hitogo.button.core.Button;
import org.hitogo.core.HitogoContainer;

public class SnackbarAlertBuilderImpl extends AlertBuilderImpl<SnackbarAlertBuilder, SnackbarAlert> implements SnackbarAlertBuilder {

    public SnackbarAlertBuilderImpl(@NonNull Class<? extends AlertImpl> targetClass,
                                    @NonNull Class<? extends AlertParams> paramClass,
                                    @NonNull AlertParamsHolder holder,
                                    @NonNull HitogoContainer container) {
        super(targetClass, paramClass, holder, container, AlertType.OTHER);
    }

    @NonNull
    @Override
    public SnackbarAlertBuilder setText(CharSequence text) {
        return this;
    }

    @NonNull
    @Override
    public SnackbarAlertBuilder setText(int resId) {
        return this;
    }

    @NonNull
    @Override
    public SnackbarAlertBuilder addAction(Button button) {
        return this;
    }

    @NonNull
    @Override
    public SnackbarAlertBuilder setActionTextColor(int color) {
        return this;
    }

    @NonNull
    @Override
    public SnackbarAlertBuilder setActionTextColor(ColorStateList colors) {
        return this;
    }

    @NonNull
    @Override
    public SnackbarAlertBuilder addCallback(Snackbar.Callback callback) {
        return this;
    }

    @NonNull
    @Override
    public SnackbarAlertBuilder setDuration(int duration) {
        return this;
    }

    @Override
    protected void onProvideData(AlertParamsHolder holder) {
        super.onProvideData(holder);


    }
}

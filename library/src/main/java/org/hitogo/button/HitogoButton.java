package org.hitogo.button;

import android.support.annotation.NonNull;
import android.util.Log;

import org.hitogo.core.HitogoUtils;

import java.security.InvalidParameterException;

@SuppressWarnings({"WeakerAccess", "unused"})
public final class HitogoButton extends HitogoButtonObject<HitogoButtonParams> {

    @Override
    protected void onCheck(@NonNull HitogoButtonParams params) {
        if (params.getText() == null || HitogoUtils.isEmpty(params.getText())) {
            Log.w(HitogoButtonBuilder.class.getName(), "Button has no text. If you want to " +
                    "display a button with only one icon, you can ignore this warning.");
        }

        if (params.hasButtonView() && (params.getViewIds() == null || params.getViewIds().length == 0)) {
            throw new InvalidParameterException("Have you forgot to add at least one view id for " +
                    "this button?");
        }

        if (params.hasButtonView()) {
            for (int id : params.getViewIds()) {
                if (id == -1) {
                    throw new InvalidParameterException("Button view id cannot be -1.");
                }
            }
        }
    }

    @Override
    protected void onCreate(@NonNull HitogoButtonParams params) {
        //doing nothing here - not needed for this button implementation
        //can be used to initialise certain parameter for the params object based on existing ones
    }
}

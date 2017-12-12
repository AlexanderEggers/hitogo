package org.hitogo.button.action;

import android.support.annotation.NonNull;
import android.util.Log;

import org.hitogo.button.core.ButtonImpl;
import org.hitogo.core.HitogoUtils;

import java.security.InvalidParameterException;

@SuppressWarnings({"WeakerAccess", "unused"})
public class ActionButtonImpl extends ButtonImpl<ActionButtonParams> implements ActionButton {

    @Override
    protected void onCheck(@NonNull ActionButtonParams params) {
        if (HitogoUtils.isEmpty(params.getText())) {
            Log.w(ActionButtonBuilder.class.getName(), "Button has no text. If you want to " +
                    "display a button with only one icon, you can ignore this warning.");
        }

        if (params.hasButtonView() && (params.getViewIds() == null || params.getViewIds().length == 0)) {
            throw new InvalidParameterException("Have you forgot to add at least one view id for " +
                    "this button?");
        }
    }

    @Override
    public final boolean equals(final Object obj) {
        return obj != null && obj instanceof ActionButtonImpl && hashCode() == obj.hashCode();
    }

    @Override
    public int hashCode() {
        return getParams().getText() != null ? getParams().getText().hashCode() : 0;
    }
}

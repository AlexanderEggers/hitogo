package org.hitogo.button.view;

import android.support.annotation.NonNull;
import android.util.Log;

import org.hitogo.button.core.ButtonImpl;

import java.security.InvalidParameterException;

@SuppressWarnings({"WeakerAccess", "unused"})
public class ViewButtonImpl extends ButtonImpl<ViewButtonParams> implements ViewButton {

    @Override
    protected void onCheck(@NonNull ViewButtonParams params) {
        super.onCheck(params);

        if (getHelper().isEmpty(params.getText())) {
            Log.w(ViewButtonBuilderImpl.class.getName(), "Button has no text. If you want to " +
                    "display a button with only one icon, you can ignore this warning.");
        }

        if (!params.hasButtonView()) {
            throw new InvalidParameterException("Have you forgot to add at least one view id for " +
                    "this button? If you don't need a view for your button, use asSimpleButton instead.");
        }

        checkViewIds(params);
    }

    protected void checkViewIds(@NonNull ViewButtonParams params) {
        if(params.hasButtonView() && params.getViewIds()[1] == -1) {
            params.getViewIds()[1] = params.getViewIds()[0];
        }
    }

    @Override
    public boolean equals(final Object obj) {
        return obj != null && obj instanceof ViewButtonImpl && hashCode() == obj.hashCode();
    }

    @Override
    public int hashCode() {
        return getParams().getText() != null ? getParams().getText().hashCode() : 0;
    }
}

package org.hitogo.button.simple;

import android.support.annotation.NonNull;

import org.hitogo.button.core.ButtonImpl;

import java.security.InvalidParameterException;

public class SimpleButtonImpl extends ButtonImpl<SimpleButtonParams> implements SimpleButton {

    @Override
    protected void onCheck(@NonNull SimpleButtonParams params) {
        super.onCheck(params);

        if (getHelper().isEmpty(params.getText())) {
            throw new InvalidParameterException("You need to add a text to this button.");
        }
    }

    @Override
    public boolean equals(final Object obj) {
        return obj != null && obj instanceof SimpleButtonImpl && hashCode() == obj.hashCode();
    }

    @Override
    public int hashCode() {
        return getParams().getText() != null ? getParams().getText().hashCode() : 0;
    }
}

package org.hitogo.button.simple;

import android.support.annotation.NonNull;
import android.util.Log;

import org.hitogo.button.core.ButtonImpl;

import java.security.InvalidParameterException;

public class SimpleButtonImpl extends ButtonImpl<SimpleButtonParams> implements SimpleButton {

    @Override
    protected void onCheck(@NonNull SimpleButtonParams params) {
        super.onCheck(params);

        if (params.getTextMap().size() == 0) {
            throw new InvalidParameterException("You need to add a text to this button.");
        }

        if (params.getTextMap().size() > 0) {
            Log.w(SimpleButtonImpl.class.getName(), "This button only supports one text " +
                    "element. Any other added text elements are not used.");
        }
    }

    @Override
    public boolean equals(final Object obj) {
        return obj != null && obj instanceof SimpleButtonImpl && hashCode() == obj.hashCode();
    }

    @Override
    public int hashCode() {
        return getParams().getTextMap().valueAt(0).hashCode();
    }
}

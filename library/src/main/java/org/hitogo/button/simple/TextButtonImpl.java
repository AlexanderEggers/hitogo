package org.hitogo.button.simple;

import android.support.annotation.NonNull;
import android.util.Log;

import org.hitogo.button.core.ButtonImpl;

import java.security.InvalidParameterException;

public class TextButtonImpl extends ButtonImpl<TextButtonParams> implements TextButton {

    @Override
    protected void onCheck(@NonNull TextButtonParams params) {
        super.onCheck(params);

        if (params.getTextMap().size() == 0) {
            throw new InvalidParameterException("You need to add a text to this button.");
        }

        if (params.getTextMap().size() > 0) {
            Log.w(TextButtonImpl.class.getName(), "This button only supports one text " +
                    "element. Any other added text elements are not used.");
        }
    }

    @Override
    public boolean equals(final Object obj) {
        return obj != null && obj instanceof TextButtonImpl && hashCode() == obj.hashCode();
    }

    @Override
    public int hashCode() {
        return getParams().getTextMap().valueAt(0).hashCode();
    }
}

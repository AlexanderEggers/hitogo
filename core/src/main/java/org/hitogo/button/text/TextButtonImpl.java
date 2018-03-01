package org.hitogo.button.text;

import android.support.annotation.NonNull;
import android.util.Log;

import org.hitogo.button.core.ButtonImpl;

import java.security.InvalidParameterException;

/**
 * Implementation for the text button. This button requires to have at least one text element.
 *
 * @since 1.0.0
 */
public class TextButtonImpl extends ButtonImpl<TextButtonParams> implements TextButton {

    @Override
    protected void onCheck(@NonNull TextButtonParams params) {
        super.onCheck(params);

        if (params.getTextMap().size() == 0) {
            throw new InvalidParameterException("You need to add a text to this button.");
        }

        if (params.getTextMap().size() > 1) {
            Log.d(TextButtonImpl.class.getName(), "This button has more than one text " +
                    "element. Make sure your alert supports that.");
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

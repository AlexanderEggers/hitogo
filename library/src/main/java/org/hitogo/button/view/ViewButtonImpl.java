package org.hitogo.button.view;

import android.support.annotation.NonNull;
import android.util.Log;
import android.util.SparseArray;

import org.hitogo.button.core.ButtonImpl;

import java.security.InvalidParameterException;

/**
 * Implementation for the view button. This button requires to have at least a non-null click view
 * id.
 *
 * @since 1.0.0
 */
public class ViewButtonImpl extends ButtonImpl<ViewButtonParams> implements ViewButton {

    @Override
    protected void onCheck(@NonNull ViewButtonParams params) {
        super.onCheck(params);

        if (!params.hasButtonView()) {
            throw new InvalidParameterException("Have you forgot to add at least one view id for " +
                    "this button? If you don't need a view for your button, use asTextButton instead.");
        }

        if (params.getTextMap().size() == 0) {
            Log.w(ViewButtonBuilderImpl.class.getName(), "Button has no text. If you want to " +
                    "display a button without text, you can ignore this warning.");
        }

        if (params.getTextMap().size() > 1) {
            Log.d(ViewButtonBuilderImpl.class.getName(), "This button has more than one text " +
                    "element. Make sure your alert supports that.");
        }
    }

    @Override
    public boolean equals(final Object obj) {
        return obj != null && obj instanceof ViewButtonImpl && hashCode() == obj.hashCode();
    }

    @Override
    public int hashCode() {
        int hashCode = 0;

        SparseArray<String> textMap = getParams().getTextMap();
        for (int i = 0; i < textMap.size(); i++) {
            String text = textMap.valueAt(i);
            hashCode += text.hashCode();
        }

        return hashCode;
    }
}

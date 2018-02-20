package org.hitogo.button.view;

import android.support.annotation.NonNull;
import android.util.Log;
import android.util.SparseArray;

import org.hitogo.button.core.ButtonImpl;

import java.security.InvalidParameterException;

@SuppressWarnings({"WeakerAccess", "unused"})
public class ViewButtonImpl extends ButtonImpl<ViewButtonParams> implements ViewButton {

    @Override
    protected void onCheck(@NonNull ViewButtonParams params) {
        super.onCheck(params);

        if (params.getTextMap().size() == 0) {
            Log.w(ViewButtonBuilderImpl.class.getName(), "Button has no text. If you want to " +
                    "display a button with only one icon, you can ignore this warning.");
        }

        if (!params.hasButtonView()) {
            throw new InvalidParameterException("Have you forgot to add at least one view id for " +
                    "this button? If you don't need a view for your button, use asSimpleButton instead.");
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

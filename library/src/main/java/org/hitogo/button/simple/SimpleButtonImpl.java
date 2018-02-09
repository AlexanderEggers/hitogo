package org.hitogo.button.simple;

import android.support.annotation.NonNull;
import android.util.Log;

import org.hitogo.button.core.ButtonImpl;
import org.hitogo.core.HitogoHelper;

public class SimpleButtonImpl extends ButtonImpl<SimpleButtonParams> implements SimpleButton {

    @Override
    protected void onCheck(@NonNull SimpleButtonParams params) {
        super.onCheck(params);

        if (getHelper().isEmpty(params.getText())) {
            Log.w(SimpleButtonImpl.class.getName(), "Button has no text. If you want to " +
                    "display a button with only one icon, you can ignore this warning.");
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

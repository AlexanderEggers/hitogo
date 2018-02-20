package org.hitogo.button.close;

import android.support.annotation.NonNull;

import org.hitogo.button.view.ViewButtonImpl;
import org.hitogo.button.view.ViewButtonParams;

@SuppressWarnings({"WeakerAccess", "unused"})
public class CloseButtonImpl extends ViewButtonImpl {

    @Override
    protected void checkViewIds(@NonNull ViewButtonParams params) {
        if(params.hasButtonView() && params.getViewIds()[1] == -1) {
            Integer defaultCloseClickId = getController().provideDefaultCloseClickId();
            params.getViewIds()[1] = defaultCloseClickId != null ? defaultCloseClickId : params.getViewIds()[0];
        }
    }
}

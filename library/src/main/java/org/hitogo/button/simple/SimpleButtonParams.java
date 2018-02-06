package org.hitogo.button.simple;

import org.hitogo.button.core.ButtonParams;
import org.hitogo.button.core.ButtonParamsHolder;

public class SimpleButtonParams extends ButtonParams {

    @Override
    protected void onCreateParams(ButtonParamsHolder holder, ButtonParams alertParams) {
        //do nothing
    }

    public int[] getViewIds() {
        return new int[0];
    }

    public boolean hasButtonView() {
        return false;
    }
}

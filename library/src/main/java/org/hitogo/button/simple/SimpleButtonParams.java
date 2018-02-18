package org.hitogo.button.simple;

import org.hitogo.button.core.ButtonParams;
import org.hitogo.core.HitogoParamsHolder;

public class SimpleButtonParams extends ButtonParams {

    @Override
    protected void onCreateParams(HitogoParamsHolder holder) {
        //do nothing
    }

    public int[] getViewIds() {
        return new int[0];
    }

    public boolean hasButtonView() {
        return false;
    }
}

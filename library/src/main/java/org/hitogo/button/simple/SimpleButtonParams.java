package org.hitogo.button.simple;

import org.hitogo.button.core.ButtonParams;
import org.hitogo.core.HitogoParamsHolder;

public class SimpleButtonParams extends ButtonParams {

    @Override
    protected void onCreateParams(HitogoParamsHolder holder) {
        //do nothing
    }

    @Override
    public int getIconId() {
        return -1;
    }

    @Override
    public Integer getClickId() {
        return null;
    }

    @Override
    public boolean hasButtonView() {
        return false;
    }
}

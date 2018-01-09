package org.hitogo.button.core;

import android.support.annotation.Nullable;

import org.hitogo.core.HitogoParamsHolder;

public class ButtonParamsHolder extends HitogoParamsHolder {

    private ButtonListener listener;

    @Nullable
    public int[] getIntList(String key) {
        return getBundle().getIntArray(key);
    }

    public void provideIntArray(String key, int[] value) {
        if(!hasKey(key)) {
            getBundle().putIntArray(key, value);
        }
    }

    public void provideButtonListener(@Nullable ButtonListener listener) {
        this.listener = listener;
    }

    @Nullable
    public ButtonListener getListener() {
        return listener;
    }
}

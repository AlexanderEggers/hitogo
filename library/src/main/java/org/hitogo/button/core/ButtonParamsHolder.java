package org.hitogo.button.core;

import android.support.annotation.Nullable;

import org.hitogo.core.HitogoParamsHolder;

@SuppressWarnings("WeakerAccess")
public class ButtonParamsHolder extends HitogoParamsHolder {

    private ButtonListener listener;
    private Object buttonParameter;

    @Nullable
    public int[] getIntList(String key) {
        return getHolderBundle().getIntArray(key);
    }

    public void provideIntArray(String key, int[] value) {
        if(!hasKey(key)) {
            getHolderBundle().putIntArray(key, value);
        }
    }

    public void provideButtonParameter(Object buttonParameter) {
        this.buttonParameter = buttonParameter;
    }

    public void provideButtonListener(@Nullable ButtonListener listener) {
        this.listener = listener;
    }

    @Nullable
    public ButtonListener getListener() {
        return listener;
    }

    @Nullable
    public Object getButtonParameter() {
        return buttonParameter;
    }
}

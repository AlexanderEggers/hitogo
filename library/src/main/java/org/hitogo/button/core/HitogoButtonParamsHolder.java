package org.hitogo.button.core;

import android.support.annotation.Nullable;

import org.hitogo.core.HitogoParamsHolder;

public class HitogoButtonParamsHolder extends HitogoParamsHolder {

    private HitogoButtonListener listener;

    @Nullable
    public final int[] getIntList(String key) {
        return getBundle().getIntArray(key);
    }

    public final void provideIntArray(String key, int[] value) {
        checkForKey(key);
        getBundle().putIntArray(key, value);
    }

    public final void provideButtonListener(@Nullable HitogoButtonListener listener) {
        this.listener = listener;
    }

    @Nullable
    HitogoButtonListener getListener() {
        return listener;
    }
}

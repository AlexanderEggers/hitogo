package org.hitogo.button;

import android.os.Bundle;

public final class HitogoParamsHolder {

    private Bundle bundle = new Bundle();

    private HitogoButtonListener listener;

    public final String getString(String key) {
        return bundle.getString(key);
    }

    public final Integer getInteger(String key) {
        return (Integer) bundle.getSerializable(key);
    }

    public final int[] getIntList(String key) {
        return bundle.getIntArray(key);
    }

    public final Boolean getBoolean(String key) {
        return bundle.getBoolean(key);
    }

    public final void provideString(String key, String value) {
        bundle.putString(key, value);
    }

    public final void provideInteger(String key, Integer value) {
        bundle.putSerializable(key, value);
    }

    public final void provideIntArray(String key, int[] value) {
        bundle.putIntArray(key, value);
    }

    public final void provideBoolean(String key, Boolean value) {
        bundle.putSerializable(key, value);
    }

    public final void provideButtonListener(HitogoButtonListener listener) {
        this.listener = listener;
    }

    HitogoButtonListener getListener() {
        return listener;
    }
}

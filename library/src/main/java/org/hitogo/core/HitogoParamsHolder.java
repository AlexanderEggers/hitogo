package org.hitogo.core;

import android.os.Bundle;
import android.support.annotation.NonNull;

public class HitogoParamsHolder {

    private Bundle bundle = new Bundle();

    public final String getString(String key) {
        return bundle.getString(key);
    }

    public final Integer getInteger(String key) {
        return (Integer) bundle.getSerializable(key);
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

    public final void provideBoolean(String key, Boolean value) {
        bundle.putSerializable(key, value);
    }

    @NonNull
    protected Bundle getBundle() {
        return bundle;
    }
}

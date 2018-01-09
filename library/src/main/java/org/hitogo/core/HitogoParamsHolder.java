package org.hitogo.core;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;

@SuppressWarnings("WeakerAccess")
public class HitogoParamsHolder {

    private Bundle bundle = new Bundle();

    public void provideString(String key, String value) {
        if(!hasKey(key)) {
            bundle.putString(key, value);
        }
    }

    public void provideFloat(String key, Float value) {
        if(!hasKey(key)) {
            bundle.putSerializable(key, value);
        }
    }

    public void provideInteger(String key, Integer value) {
        if(!hasKey(key)) {
            bundle.putSerializable(key, value);
        }
    }

    public void provideBoolean(String key, boolean value) {
        if(!hasKey(key)) {
            bundle.putBoolean(key, value);
        }
    }

    protected boolean hasKey(String key) {
        if(bundle.containsKey(key)) {
            Log.e(HitogoParams.class.getName(), "Error: Your builder value will be overridden by" +
                    " a private value. Make sure that you don't clash with private value keys!");
            Log.e(HitogoParams.class.getName(), "Key that will be overridden: " + key);
            return true;
        }
        return false;
    }

    public String getString(String key) {
        return bundle.getString(key);
    }

    public Integer getInteger(String key) {
        return (Integer) bundle.getSerializable(key);
    }

    public Float getFloat(String key) {
        return (Float) bundle.getSerializable(key);
    }

    public boolean getBoolean(String key) {
        return bundle.getBoolean(key);
    }

    @NonNull
    protected Bundle getBundle() {
        return bundle;
    }
}

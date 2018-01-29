package org.hitogo.core;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;

import java.io.Serializable;

@SuppressWarnings("WeakerAccess")
public class HitogoParamsHolder {

    private Bundle bundle = new Bundle();

    public void provideString(String key, String value) {
        if (!hasKey(key)) {
            bundle.putString(key, value);
        }
    }

    public void provideFloat(String key, Float value) {
        if (!hasKey(key)) {
            bundle.putSerializable(key, value);
        }
    }

    public void provideDouble(String key, Double value) {
        if (!hasKey(key)) {
            bundle.putSerializable(key, value);
        }
    }

    public void provideInteger(String key, Integer value) {
        if (!hasKey(key)) {
            bundle.putSerializable(key, value);
        }
    }

    public void provideBoolean(String key, boolean value) {
        if (!hasKey(key)) {
            bundle.putBoolean(key, value);
        }
    }

    public void provideLong(String key, Long value) {
        if (!hasKey(key)) {
            bundle.putSerializable(key, value);
        }
    }

    public void provideSerializable(String key, Serializable value) {
        if (!hasKey(key)) {
            bundle.putSerializable(key, value);
        }
    }

    protected boolean hasKey(String key) {
        if (bundle.containsKey(key)) {
            Log.e(HitogoParams.class.getName(), "Cannot save value. Reason: Your builder value " +
                    "is trying to override a private value. Make sure that you don't clash with " +
                    "private value keys! Existing key is:" + key);
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

    public Double getDouble(String key) {
        return (Double) bundle.getSerializable(key);
    }

    public boolean getBoolean(String key) {
        return bundle.getBoolean(key);
    }

    public Long getLong(String key) {
        return (Long) bundle.getSerializable(key);
    }

    public Serializable getSerializable(String key) {
        return bundle.getSerializable(key);
    }

    @NonNull
    protected Bundle getBundle() {
        return bundle;
    }
}

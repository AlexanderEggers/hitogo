package org.hitogo.core;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;

import java.io.Serializable;

/**
 * This class is used to save and provide alert-related data. The builder classes are using this
 * object when the method <u>build</u> has been called. To ensure that the builder values are used
 * correctly, this class is only used to communicate between the builder and the HitogoParams object.
 *
 * @since 1.0.0
 */
@SuppressWarnings("WeakerAccess")
public class HitogoParamsHolder {

    private Bundle bundle = new Bundle();

    /**
     * Inserts a value into the holder object, if no value is existing for the given key.
     *
     * @param key   a String, or null
     * @param value a String object, or null
     * @since 1.0.0
     */
    public void provideString(String key, String value) {
        if (!hasKey(key)) {
            bundle.putString(key, value);
        }
    }

    /**
     * Inserts a value into the holder object, if no value is existing for the given key.
     *
     * @param key   a String, or null
     * @param value a Float object, or null
     * @since 1.0.0
     */
    public void provideFloat(String key, Float value) {
        if (!hasKey(key)) {
            bundle.putSerializable(key, value);
        }
    }

    /**
     * Inserts a value into the holder object, if no value is existing for the given key.
     *
     * @param key   a String, or null
     * @param value a Double object, or null
     * @since 1.0.0
     */
    public void provideDouble(String key, Double value) {
        if (!hasKey(key)) {
            bundle.putSerializable(key, value);
        }
    }

    /**
     * Inserts a value into the holder object, if no value is existing for the given key.
     *
     * @param key   a String, or null
     * @param value a Integer object, or null
     * @since 1.0.0
     */
    public void provideInteger(String key, Integer value) {
        if (!hasKey(key)) {
            bundle.putSerializable(key, value);
        }
    }

    /**
     * Inserts a value into the holder object, if no value is existing for the given key.
     *
     * @param key   a String, or null
     * @param value a boolean value
     * @since 1.0.0
     */
    public void provideBoolean(String key, boolean value) {
        if (!hasKey(key)) {
            bundle.putBoolean(key, value);
        }
    }

    /**
     * Inserts a value into the holder object, if no value is existing for the given key.
     *
     * @param key   a String, or null
     * @param value a Long object, or null
     * @since 1.0.0
     */
    public void provideLong(String key, Long value) {
        if (!hasKey(key)) {
            bundle.putSerializable(key, value);
        }
    }

    /**
     * Inserts a value into the holder object, if no value is existing for the given key.
     *
     * @param key   a String, or null
     * @param value a Serializable object, or null
     * @since 1.0.0
     */
    public void provideSerializable(String key, Serializable value) {
        if (!hasKey(key)) {
            bundle.putSerializable(key, value);
        }
    }

    /**
     * Inserts a value into the holder object, if no value is existing for the given key.
     *
     * @param key   a String, or null
     * @param value a Bundle object, or null
     * @since 1.0.0
     */
    public void provideBundle(String key, Bundle value) {
        if (!hasKey(key)) {
            bundle.putBundle(key, value);
        }
    }

    /**
     * Checks if the given key is already taken within this HitogoParamsHolder instance.
     *
     * @param key Key which needs to be checked.
     * @return True if the key has been found, false otherwise.
     * @since 1.0.0
     */
    public boolean hasKey(String key) {
        if (bundle.containsKey(key)) {
            Log.e(HitogoParams.class.getName(), "Cannot save value. Reason: Your builder value " +
                    "is trying to override a private value. Make sure that you don't clash with " +
                    "private value keys! Existing key is:" + key);
            return true;
        }
        return false;
    }

    /**
     * Returns the value associated with the given key, or null if
     * no mapping of the desired type exists for the given key or a null
     * value is explicitly associated with the key.
     *
     * @param key a String, or null
     * @return a String object, or null
     * @since 1.0.0
     */
    public String getString(String key) {
        return bundle.getString(key);
    }

    /**
     * Returns the value associated with the given key, or null if
     * no mapping of the desired type exists for the given key or a null
     * value is explicitly associated with the key.
     *
     * @param key a String, or null
     * @return a Integer object, or null
     * @since 1.0.0
     */
    public Integer getInteger(String key) {
        return (Integer) bundle.getSerializable(key);
    }

    /**
     * Returns the value associated with the given key, or null if
     * no mapping of the desired type exists for the given key or a null
     * value is explicitly associated with the key.
     *
     * @param key a String, or null
     * @return a Float object, or null
     * @since 1.0.0
     */
    public Float getFloat(String key) {
        return (Float) bundle.getSerializable(key);
    }

    /**
     * Returns the value associated with the given key, or null if
     * no mapping of the desired type exists for the given key or a null
     * value is explicitly associated with the key.
     *
     * @param key a String, or null
     * @return a Double object, or null
     * @since 1.0.0
     */
    public Double getDouble(String key) {
        return (Double) bundle.getSerializable(key);
    }

    /**
     * Returns the value associated with the given key, or false if
     * no mapping of the desired type exists for the given key.
     *
     * @param key a String
     * @return a boolean value
     * @since 1.0.0
     */
    public Boolean getBoolean(String key) {
        return bundle.getBoolean(key);
    }

    /**
     * Returns the value associated with the given key, or null if
     * no mapping of the desired type exists for the given key or a null
     * value is explicitly associated with the key.
     *
     * @param key a String, or null
     * @return a Long object, or null
     * @since 1.0.0
     */
    public Long getLong(String key) {
        return (Long) bundle.getSerializable(key);
    }

    /**
     * Returns the value associated with the given key, or null if
     * no mapping of the desired type exists for the given key or a null
     * value is explicitly associated with the key.
     *
     * @param key a String, or null
     * @return a Serializable object, or null
     * @since 1.0.0
     */
    public Serializable getSerializable(String key) {
        return bundle.getSerializable(key);
    }

    /**
     * Returns the value associated with the given key, or null if
     * no mapping of the desired type exists for the given key or a null
     * value is explicitly associated with the key.
     *
     * @param key a String, or null
     * @return a Bundle object, or null
     * @since 1.0.0
     */
    public Bundle getBundle(String key) {
        return bundle.getBundle(key);
    }

    /**
     * Returns the bundle object for this holder instance.
     *
     * @return a Bundle
     * @since 1.0.0
     */
    @NonNull
    protected Bundle getHolderBundle() {
        return bundle;
    }
}

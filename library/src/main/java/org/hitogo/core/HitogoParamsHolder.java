package org.hitogo.core;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.Serializable;
import java.util.HashMap;

/**
 * This class is used to save and provide alert-related data. The builder classes are using this
 * object when the method <u>build</u> has been called. To ensure that the builder values are used
 * correctly, this class is only used to communicate between the builder and the HitogoParams object.
 *
 * @since 1.0.0
 */
@SuppressWarnings("WeakerAccess")
public class HitogoParamsHolder {

    private final Bundle bundle = new Bundle();
    private final HashMap<String, Object> customObjects = new HashMap<>();

    /**
     * Inserts a value into the holder object, if no value is existing for the given key.
     *
     * @param key   a String
     * @param value an object, or null
     * @since 1.0.0
     */
    public void provideCustomObject(@NonNull String key, @Nullable Object value) {
        if (!hasKey(key)) {
            customObjects.put(key, value);
        }
    }

    /**
     * Inserts a value into the holder object, if no value is existing for the given key.
     *
     * @param key a String
     * @param value a String object, or null
     * @since 1.0.0
     */
    public void provideString(@NonNull String key, @Nullable String value) {
        if (!hasKey(key)) {
            bundle.putString(key, value);
        }
    }

    /**
     * Inserts a value into the holder object, if no value is existing for the given key.
     *
     * @param key a String
     * @param value a Float object, or null
     * @since 1.0.0
     */
    public void provideFloat(@NonNull String key, @Nullable Float value) {
        if (!hasKey(key)) {
            bundle.putSerializable(key, value);
        }
    }

    /**
     * Inserts a value into the holder object, if no value is existing for the given key.
     *
     * @param key a String
     * @param value a Double object, or null
     * @since 1.0.0
     */
    public void provideDouble(@NonNull String key, @Nullable Double value) {
        if (!hasKey(key)) {
            bundle.putSerializable(key, value);
        }
    }

    /**
     * Inserts a value into the holder object, if no value is existing for the given key.
     *
     * @param key a String
     * @param value a Integer object, or null
     * @since 1.0.0
     */
    public void provideInteger(@NonNull String key, @Nullable Integer value) {
        if (!hasKey(key)) {
            bundle.putSerializable(key, value);
        }
    }

    /**
     * Inserts a value into the holder object, if no value is existing for the given key.
     *
     * @param key a String
     * @param value a boolean value
     * @since 1.0.0
     */
    public void provideBoolean(@NonNull String key, boolean value) {
        if (!hasKey(key)) {
            bundle.putBoolean(key, value);
        }
    }

    /**
     * Inserts a value into the holder object, if no value is existing for the given key.
     *
     * @param key a String
     * @param value a Long object, or null
     * @since 1.0.0
     */
    public void provideLong(@NonNull String key, @Nullable Long value) {
        if (!hasKey(key)) {
            bundle.putSerializable(key, value);
        }
    }

    /**
     * Inserts a value into the holder object, if no value is existing for the given key.
     *
     * @param key a String
     * @param value a Serializable object, or null
     * @since 1.0.0
     */
    public void provideSerializable(@NonNull String key, @Nullable Serializable value) {
        if (!hasKey(key)) {
            bundle.putSerializable(key, value);
        }
    }

    /**
     * Inserts a value into the holder object, if no value is existing for the given key.
     *
     * @param key a String
     * @param value a Bundle object, or null
     * @since 1.0.0
     */
    public void provideBundle(@NonNull String key, @Nullable Bundle value) {
        if (!hasKey(key)) {
            bundle.putBundle(key, value);
        }
    }

    /**
     * Checks if the given key is already taken within this HitogoParamsHolder instance.
     *
     * @param key a String
     * @return True if the key has been found, false otherwise.
     * @since 1.0.0
     */
    public boolean hasKey(@NonNull String key) {
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
     * @param key a String
     * @return a String object, or null
     * @since 1.0.0
     */
    public String getString(@NonNull String key) {
        return bundle.getString(key);
    }

    /**
     * Returns the value associated with the given key, or null if
     * no mapping of the desired type exists for the given key or a null
     * value is explicitly associated with the key.
     *
     * @param key a String
     * @return a Integer object, or null
     * @since 1.0.0
     */
    public Integer getInteger(@NonNull String key) {
        return (Integer) bundle.getSerializable(key);
    }

    /**
     * Returns the value associated with the given key, or null if
     * no mapping of the desired type exists for the given key or a null
     * value is explicitly associated with the key.
     *
     * @param key a String
     * @return a Float object, or null
     * @since 1.0.0
     */
    public Float getFloat(@NonNull String key) {
        return (Float) bundle.getSerializable(key);
    }

    /**
     * Returns the value associated with the given key, or null if
     * no mapping of the desired type exists for the given key or a null
     * value is explicitly associated with the key.
     *
     * @param key a String
     * @return a Double object, or null
     * @since 1.0.0
     */
    public Double getDouble(@NonNull String key) {
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
    public Boolean getBoolean(@NonNull String key) {
        return bundle.getBoolean(key);
    }

    /**
     * Returns the value associated with the given key, or null if
     * no mapping of the desired type exists for the given key or a null
     * value is explicitly associated with the key.
     *
     * @param key a String
     * @return a Long object, or null
     * @since 1.0.0
     */
    public Long getLong(@NonNull String key) {
        return (Long) bundle.getSerializable(key);
    }

    /**
     * Returns the value associated with the given key, or null if
     * no mapping of the desired type exists for the given key or a null
     * value is explicitly associated with the key.
     *
     * @param key a String
     * @return a Serializable object, or null
     * @since 1.0.0
     */
    @SuppressWarnings("unchecked")
    public <T extends Serializable> T getSerializable(@NonNull String key) {
        return (T) bundle.getSerializable(key);
    }

    /**
     * Returns the value associated with the given key, or null if
     * no mapping of the desired type exists for the given key or a null
     * value is explicitly associated with the key.
     *
     * @param key a String
     * @return a Bundle object, or null
     * @since 1.0.0
     */
    public Bundle getBundle(@NonNull String key) {
        return bundle.getBundle(key);
    }

    /**
     * Returns the value associated with the given key, or null if
     * no mapping of the desired type exists for the given key or a null
     * value is explicitly associated with the key.
     *
     * @param key a String
     * @return an object, or null
     * @since 1.0.0
     */
    @SuppressWarnings("unchecked")
    public <T> T getCustomObject(@NonNull String key) {
        return (T) customObjects.get(key);
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

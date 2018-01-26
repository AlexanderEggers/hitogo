package org.hitogo.button.core;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.util.Log;

import org.hitogo.core.HitogoContainer;
import org.hitogo.core.HitogoController;
import org.hitogo.core.HitogoUtils;

import java.lang.ref.WeakReference;

@SuppressWarnings({"WeakerAccess", "unused", "unchecked"})
public abstract class ButtonBuilder<C extends ButtonBuilder, B extends Button> {

    private Class<? extends ButtonImpl> targetClass;
    private Class<? extends ButtonParams> paramClass;
    private WeakReference<HitogoContainer> containerRef;

    private ButtonParamsHolder holder = new ButtonParamsHolder();

    private Bundle privateBundle = new Bundle();
    private ButtonType builderType;

    private String text;
    private boolean closeAfterClick = true;
    private ButtonListener listener;
    private Object buttonParameter;

    public ButtonBuilder(@NonNull Class<? extends ButtonImpl> targetClass,
                         @NonNull Class<? extends ButtonParams> paramClass,
                         @NonNull HitogoContainer container, ButtonType builderType) {
        this.targetClass = targetClass;
        this.paramClass = paramClass;
        this.containerRef = new WeakReference<>(container);
        this.builderType = builderType;
    }

    @NonNull
    public C setText(String text) {
        this.text = text;
        return (C) this;
    }

    @NonNull
    public C setText(@StringRes int textRes) {
        this.text = HitogoUtils.getStringRes(getContainer().getActivity(), textRes);
        return (C) this;
    }

    @NonNull
    public <T> C setButtonListener(@Nullable ButtonListener<T> listener) {
        return setButtonListener(listener, true, null);
    }

    @NonNull
    public <T> C setButtonListener(@Nullable ButtonListener<T> listener, T buttonParameter) {
        return setButtonListener(listener, true, buttonParameter);
    }

    @NonNull
    public <T> C setButtonListener(@Nullable ButtonListener<T> listener, boolean closeAfterClick) {
        return setButtonListener(listener, closeAfterClick, null);
    }

    @NonNull
    public <T> C setButtonListener(@Nullable ButtonListener<T> listener, boolean closeAfterClick, T buttonParameter) {
        this.listener = listener;
        this.closeAfterClick = closeAfterClick;
        this.buttonParameter = buttonParameter;
        return (C) this;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public B build() {
        onProvideData(holder);
        onProvidePrivateData(holder);

        try {
            ButtonImpl object = targetClass.getConstructor().newInstance();
            ButtonParams params = paramClass.getConstructor().newInstance();
            params.provideData(holder, privateBundle);
            return (B) object.create(getContainer(), params);
        } catch (Exception e) {
            Log.wtf(ButtonBuilder.class.getName(), "Build process failed.");
            throw new IllegalStateException(e);
        }
    }

    private void onProvidePrivateData(ButtonParamsHolder holder) {
        privateBundle.putSerializable(ButtonParamsKeys.TYPE_KEY, builderType);
        privateBundle.putString(ButtonParamsKeys.TEXT_KEY, text);
        privateBundle.putBoolean(ButtonParamsKeys.CLOSE_AFTER_CLICK_KEY, closeAfterClick);

        holder.provideButtonListener(listener);
        holder.provideButtonParameter(buttonParameter);
    }

    protected abstract void onProvideData(ButtonParamsHolder holder);

    protected final HitogoContainer getContainer() {
        return containerRef.get();
    }

    @NonNull
    protected final HitogoController getController() {
        return containerRef.get().getController();
    }
}

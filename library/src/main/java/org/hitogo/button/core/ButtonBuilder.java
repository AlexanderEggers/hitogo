package org.hitogo.button.core;

import android.support.annotation.CallSuper;
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

    private final Class<? extends ButtonImpl> targetClass;
    private final Class<? extends ButtonParams> paramClass;
    private final WeakReference<HitogoContainer> containerRef;

    private final ButtonParamsHolder holder;

    private String text;
    private boolean closeAfterClick = true;
    private ButtonListener listener;
    private Object buttonParameter;

    public ButtonBuilder(@NonNull Class<? extends ButtonImpl> targetClass,
                         @NonNull Class<? extends ButtonParams> paramClass,
                         @NonNull ButtonParamsHolder holder,
                         @NonNull HitogoContainer container) {
        this.targetClass = targetClass;
        this.paramClass = paramClass;
        this.holder = holder;
        this.containerRef = new WeakReference<>(container);
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

        try {
            ButtonImpl object = targetClass.getConstructor().newInstance();
            ButtonParams params = paramClass.getConstructor().newInstance();
            params.provideData(holder);
            return (B) object.create(getContainer(), params);
        } catch (Exception e) {
            Log.wtf(ButtonBuilder.class.getName(), "Build process failed.");
            throw new IllegalStateException(e);
        }
    }

    @CallSuper
    protected void onProvideData(ButtonParamsHolder holder) {
        holder.provideString(ButtonParamsKeys.TEXT_KEY, text);
        holder.provideBoolean(ButtonParamsKeys.CLOSE_AFTER_CLICK_KEY, closeAfterClick);

        holder.provideButtonListener(listener);
        holder.provideButtonParameter(buttonParameter);
    }

    protected final HitogoContainer getContainer() {
        return containerRef.get();
    }

    @NonNull
    protected final HitogoController getController() {
        return containerRef.get().getController();
    }
}

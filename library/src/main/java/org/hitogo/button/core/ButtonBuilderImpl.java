package org.hitogo.button.core;

import android.graphics.drawable.Drawable;
import android.support.annotation.CallSuper;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.util.SparseArray;

import org.hitogo.core.HitogoAccessor;
import org.hitogo.core.HitogoContainer;
import org.hitogo.core.HitogoController;
import org.hitogo.core.HitogoParamsHolder;
import org.hitogo.core.HitogoHelper;

import java.lang.ref.WeakReference;

@SuppressWarnings({"WeakerAccess", "unused", "unchecked"})
public abstract class ButtonBuilderImpl<C extends ButtonBuilder, B extends Button> implements ButtonBuilder<C, B> {

    private final Class<? extends ButtonImpl> targetClass;
    private final Class<? extends ButtonParams> paramClass;
    private final WeakReference<HitogoContainer> containerRef;

    private final HitogoController controller;
    private final HitogoHelper helper;
    private final HitogoAccessor accessor;
    private final ButtonType buttonType;

    private final SparseArray<String> textMap = new SparseArray<>();
    private final SparseArray<Drawable> drawableMap = new SparseArray<>();

    private boolean closeAfterClick = true;
    private ButtonListener listener;
    private Object buttonParameter;

    public ButtonBuilderImpl(@NonNull Class<? extends ButtonImpl> targetClass,
                             @NonNull Class<? extends ButtonParams> paramClass,
                             @NonNull HitogoContainer container,
                             @NonNull ButtonType buttonType) {
        this.targetClass = targetClass;
        this.paramClass = paramClass;
        this.containerRef = new WeakReference<>(container);
        this.controller = container.getController();
        this.helper = controller.provideHelper();
        this.accessor = controller.provideAccessor();
        this.buttonType = buttonType;
    }

    @Override
    @NonNull
    public C addText(@Nullable String text) {
        return addText(controller.provideDefaultButtonTextViewId(buttonType), text);
    }

    @Override
    @NonNull
    public C addText(@StringRes int textRes) {
        return addText(controller.provideDefaultButtonTextViewId(buttonType),
                accessor.getString(getContainer().getActivity(), textRes));
    }

    @NonNull
    @Override
    public C addText(@IdRes @Nullable Integer viewId, int textRes) {
        return addText(viewId, accessor.getString(getContainer().getActivity(), textRes));
    }

    @NonNull
    @Override
    public C addText(@IdRes @Nullable Integer viewId, @Nullable String text) {
        textMap.put(viewId != null ? viewId : -1, text);
        return (C) this;
    }

    @Override
    @NonNull
    public <T> C setButtonListener(@Nullable ButtonListener<T> listener) {
        return setButtonListener(listener, true, null);
    }

    @Override
    @NonNull
    public <T> C setButtonListener(@Nullable ButtonListener<T> listener, T buttonParameter) {
        return setButtonListener(listener, true, buttonParameter);
    }

    @Override
    @NonNull
    public <T> C setButtonListener(@Nullable ButtonListener<T> listener, boolean closeAfterClick) {
        return setButtonListener(listener, closeAfterClick, null);
    }

    @Override
    @NonNull
    public <T> C setButtonListener(@Nullable ButtonListener<T> listener, boolean closeAfterClick, T buttonParameter) {
        this.listener = listener;
        this.closeAfterClick = closeAfterClick;
        this.buttonParameter = buttonParameter;
        return (C) this;
    }

    @Override
    public C addDrawable(int drawableRes) {
        return addDrawable(controller.provideDefaultButtonDrawableViewId(buttonType),
                ContextCompat.getDrawable(getContainer().getActivity(), drawableRes));
    }

    @Override
    public C addDrawable(@NonNull Drawable drawable) {
        return addDrawable(controller.provideDefaultButtonDrawableViewId(buttonType), drawable);
    }

    @Override
    public C addDrawable(@IdRes @Nullable Integer viewId, int drawableRes) {
        return addDrawable(viewId, ContextCompat.getDrawable(getContainer().getActivity(), drawableRes));
    }

    @Override
    public C addDrawable(@IdRes @Nullable Integer viewId, @Nullable Drawable drawable) {
        drawableMap.put(viewId != null ? viewId : -1, drawable);
        return (C) this;
    }

    @Override
    @NonNull
    @SuppressWarnings("unchecked")
    public B build() {
        HitogoParamsHolder holder = getController().provideButtonParamsHolder(buttonType);
        onProvideData(holder);

        try {
            ButtonImpl object = targetClass.getConstructor().newInstance();
            ButtonParams params = paramClass.getConstructor().newInstance();
            params.provideData(holder, getController());
            return (B) object.create(getContainer(), params);
        } catch (Exception e) {
            Log.wtf(ButtonBuilderImpl.class.getName(), "Build process failed.");
            throw new IllegalStateException(e);
        }
    }

    @CallSuper
    protected void onProvideData(HitogoParamsHolder holder) {
        holder.provideBoolean(ButtonParamsKeys.CLOSE_AFTER_CLICK_KEY, closeAfterClick);
        holder.provideSerializable(ButtonParamsKeys.BUTTON_TYPE_KEY, buttonType);

        holder.provideCustomObject(ButtonParamsKeys.TEXT_KEY, textMap);
        holder.provideCustomObject(ButtonParamsKeys.DRAWABLE_KEY, drawableMap);
        holder.provideCustomObject(ButtonParamsKeys.BUTTON_LISTENER_KEY, listener);
        holder.provideCustomObject(ButtonParamsKeys.BUTTON_PARAMETER_KEY, buttonParameter);
    }

    @NonNull
    protected final HitogoContainer getContainer() {
        return containerRef.get();
    }

    @NonNull
    protected final HitogoController getController() {
        return controller;
    }

    @NonNull
    public ButtonType getButtonType() {
        return buttonType;
    }

    @NonNull
    protected HitogoHelper getHelper() {
        return helper;
    }

    public HitogoAccessor getAccessor() {
        return accessor;
    }
}

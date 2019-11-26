package org.hitogo.button.core;

import android.graphics.drawable.Drawable;
import androidx.annotation.CallSuper;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.core.content.ContextCompat;
import android.util.Log;
import android.util.SparseArray;

import org.hitogo.core.HitogoAccessor;
import org.hitogo.core.HitogoContainer;
import org.hitogo.core.HitogoController;
import org.hitogo.core.HitogoHelper;
import org.hitogo.core.HitogoParamsHolder;

import java.lang.ref.WeakReference;

/**
 * Base button builder which includes all basic method to assign the most important/common values to
 * the button.
 *
 * @param <C> Type for the build class which is using this class implementation.
 * @param <B> Type for the result button which is usable to execute certain methods at the end.
 * @see Button
 * @since 1.0.0
 */
@SuppressWarnings({"unchecked"})
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

    /**
     * Default constructor for the ButtonBuilderImpl.
     *
     * @param targetClass Class object for the requested button.
     * @param paramClass  Class object for the params object which is used by the button.
     * @param container   Container which is used as a reference for this button (context, view,
     *                    controller).
     * @param buttonType  ButtonType which is needed for the build and visibility process of this
     *                    alert.
     * @see HitogoContainer
     * @see HitogoController
     * @see ButtonType
     * @since 1.0.0
     */
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
    public C addText(@IdRes @Nullable Integer viewId, int textRes) {
        return addText(viewId, accessor.getString(getContainer().getActivity(), textRes));
    }

    @NonNull
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

    @NonNull
    public C addDrawable(int drawableRes) {
        return addDrawable(controller.provideDefaultButtonDrawableViewId(buttonType),
                ContextCompat.getDrawable(getContainer().getActivity(), drawableRes));
    }

    @NonNull
    public C addDrawable(@NonNull Drawable drawable) {
        return addDrawable(controller.provideDefaultButtonDrawableViewId(buttonType), drawable);
    }

    @NonNull
    public C addDrawable(@IdRes @Nullable Integer viewId, int drawableRes) {
        return addDrawable(viewId, ContextCompat.getDrawable(getContainer().getActivity(), drawableRes));
    }

    @NonNull
    public C addDrawable(@IdRes @Nullable Integer viewId, @Nullable Drawable drawable) {
        drawableMap.put(viewId != null ? viewId : -1, drawable);
        return (C) this;
    }

    @Override
    @NonNull
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

    /**
     * Returns the used HitogoContainer object for the button.
     *
     * @return HitogoContainer of the button.
     * @see HitogoContainer
     * @since 1.0.0
     */
    @NonNull
    protected HitogoContainer getContainer() {
        return containerRef.get();
    }

    /**
     * Returns the used HitogoController object for the button.
     *
     * @return HitogoController of the button.
     * @see HitogoController
     * @since 1.0.0
     */
    @NonNull
    protected HitogoController getController() {
        return controller;
    }

    /**
     * Returns the used button type for this button.
     *
     * @return a ButtonType object
     * @see ButtonType
     * @since 1.0.0
     */
    @NonNull
    protected ButtonType getButtonType() {
        return buttonType;
    }

    /**
     * Returns the used HitogoHelper object for the alert.
     *
     * @return a HitogoHelper object
     * @see HitogoHelper
     * @since 1.0.0
     */
    @NonNull
    protected HitogoHelper getHelper() {
        return helper;
    }

    /**
     * Returns the used HitogoAccessor object for the button.
     *
     * @return a HitogoAccessor object
     * @see HitogoAccessor
     * @since 1.0.0
     */
    @NonNull
    protected HitogoAccessor getAccessor() {
        return accessor;
    }
}

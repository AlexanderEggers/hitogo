package org.hitogo.alert.core;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.IdRes;
import android.support.annotation.IntRange;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.util.Log;
import android.util.SparseArray;

import org.hitogo.alert.view.ViewAlertBuilderImpl;
import org.hitogo.button.core.Button;
import org.hitogo.core.HitogoAccessor;
import org.hitogo.core.HitogoContainer;
import org.hitogo.core.HitogoController;
import org.hitogo.core.HitogoParamsHolder;
import org.hitogo.core.HitogoHelper;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Base alert builder which includes all basic method to assign the most important/common values to
 * the alert.
 *
 * @param <B> Type for the build class which is using this class implementation.
 * @param <A> Type for the result alert which is usable to execute certain methods at the end.
 * @see Alert
 * @since 1.0.0
 */
@SuppressWarnings({"unchecked"})
public abstract class AlertBuilderImpl<B, A extends Alert> implements AlertBuilder<B, A> {

    private final Class<? extends AlertImpl> targetClass;
    private final Class<? extends AlertParams> paramClass;
    private final WeakReference<HitogoContainer> containerRef;

    private final List<VisibilityListener> visibilityListener = new ArrayList<>();
    private final SparseArray<String> textMap = new SparseArray<>();
    private final SparseArray<Drawable> drawableMap = new SparseArray<>();
    private final List<Button> buttons = new ArrayList<>();

    private HitogoController controller;
    private HitogoHelper helper;
    private HitogoAccessor accessor;

    private Button closeButton;
    private String title;
    private Integer titleViewId;

    private Bundle arguments;
    private Integer state;
    private String tag;
    private AlertType alertType;
    private Integer layoutRes;
    private Integer priority;

    /**
     * Default constructor for the AlertBuilderImpl.
     *
     * @param targetClass Class object for the requested alert.
     * @param paramClass  Class object for the params object which is used by the alert.
     * @param container   Container which is used as a reference for this alert (context, view,
     *                    controller).
     * @param alertType   AlertType which is needed for the build and visibility process of this
     *                    alert.
     * @see HitogoContainer
     * @see HitogoController
     * @see AlertType
     * @since 1.0.0
     */
    public AlertBuilderImpl(@NonNull Class<? extends AlertImpl> targetClass,
                            @NonNull Class<? extends AlertParams> paramClass,
                            @NonNull HitogoContainer container,
                            @NonNull AlertType alertType) {
        this.targetClass = targetClass;
        this.paramClass = paramClass;
        this.containerRef = new WeakReference<>(container);
        this.controller = container.getController();
        this.helper = controller.provideHelper();
        this.accessor = controller.provideAccessor();
        this.alertType = alertType;
    }

    @Override
    @NonNull
    @SuppressWarnings("unchecked")
    public A build() {
        HitogoParamsHolder holder = controller.provideAlertParamsHolder(alertType);
        onProvideData(holder);

        try {
            AlertImpl object = targetClass.getConstructor().newInstance();
            AlertParams params = paramClass.getConstructor().newInstance();
            params.provideData(holder);
            return (A) object.create(getContainer(), params);
        } catch (Exception e) {
            Log.wtf(ViewAlertBuilderImpl.class.getName(), "Build process failed.");
            throw new IllegalStateException(e);
        }
    }

    /**
     * Provides builder values which are used by the implemented builder class. The given
     * HitogoParamsHolder is used to initialised the data foundation for the alert object.
     *
     * @param holder Temporary object holder for all alert values, like title or text.
     * @see HitogoParamsHolder
     * @see AlertParams
     * @since 1.0.0
     */
    @CallSuper
    protected void onProvideData(HitogoParamsHolder holder) {
        holder.provideString(AlertParamsKeys.TITLE_KEY, title);
        holder.provideString(AlertParamsKeys.TAG_KEY, tag);
        holder.provideBundle(AlertParamsKeys.ARGUMENTS_KEY, arguments);
        holder.provideSerializable(AlertParamsKeys.TITLE_VIEW_ID_KEY, titleViewId);
        holder.provideSerializable(AlertParamsKeys.TYPE_KEY, alertType);
        holder.provideSerializable(AlertParamsKeys.STATE_KEY, state);
        holder.provideSerializable(AlertParamsKeys.LAYOUT_RES_KEY, layoutRes);
        holder.provideSerializable(AlertParamsKeys.PRIORITY_KEY, priority);

        holder.provideCustomObject(AlertParamsKeys.VISIBILITY_LISTENER_KEY, visibilityListener);
        holder.provideCustomObject(AlertParamsKeys.TEXT_KEY, textMap);
        holder.provideCustomObject(AlertParamsKeys.DRAWABLE_KEY, drawableMap);
        holder.provideCustomObject(AlertParamsKeys.BUTTONS_KEY, buttons);
        holder.provideCustomObject(AlertParamsKeys.CLOSE_BUTTON_KEY, closeButton);
    }

    @Override
    @NonNull
    public B setController(@NonNull HitogoController controller) {
        this.controller = controller;
        this.helper = controller.provideHelper();
        this.accessor = controller.provideAccessor();
        return (B) this;
    }

    @Override
    @NonNull
    public B setBundle(@NonNull Bundle arguments) {
        this.arguments = arguments;
        return (B) this;
    }

    @Override
    @NonNull
    public B setTitle(@NonNull String title) {
        return setTitle(controller.provideDefaultTitleViewId(alertType), title);
    }

    @Override
    @NonNull
    public B setTitle(@StringRes int titleRes) {
        return setTitle(controller.provideDefaultTitleViewId(alertType),
                accessor.getString(getContainer().getActivity(), titleRes));
    }

    @Override
    @NonNull
    public B setTitle(@IdRes @Nullable Integer viewId, @StringRes int titleRes) {
        return setTitle(viewId, accessor.getString(getContainer().getActivity(), titleRes));
    }

    @Override
    @NonNull
    public B setTitle(@IdRes @Nullable Integer viewId, @NonNull String title) {
        this.titleViewId = viewId;
        this.title = title;
        return (B) this;
    }

    @Override
    @NonNull
    public B addText(@NonNull String text) {
        return addText(controller.provideDefaultTextViewId(alertType), text);
    }

    @Override
    @NonNull
    public B addText(@StringRes int textRes) {
        return addText(controller.provideDefaultTextViewId(alertType),
                accessor.getString(getContainer().getActivity(), textRes));
    }

    @Override
    @NonNull
    public B addText(@IdRes @Nullable Integer viewId, @StringRes int textRes) {
        return addText(viewId, accessor.getString(getContainer().getActivity(), textRes));
    }

    @Override
    @NonNull
    public B addText(@IdRes @Nullable Integer viewId, @NonNull String text) {
        textMap.put(viewId != null ? viewId : 0, text);
        return (B) this;
    }

    @Override
    @NonNull
    public B setTag(@NonNull String tag) {
        this.tag = tag;
        return (B) this;
    }

    @Override
    @NonNull
    public B setState(@IntRange(from = 0) int state) {
        this.state = state;
        return (B) this;
    }

    @Override
    @NonNull
    public B setState(@Nullable Enum state) {
        this.state = state != null ? state.ordinal() : null;
        return (B) this;
    }

    @Override
    @NonNull
    public B addVisibilityListener(@NonNull VisibilityListener<A> listener) {
        this.visibilityListener.add(listener);
        return (B) this;
    }

    @Override
    @NonNull
    public B addButton(@NonNull Button... buttons) {
        Collections.addAll(this.buttons, buttons);
        return (B) this;
    }

    @NonNull
    protected B setCloseButton(@NonNull Button closeButton) {
        this.closeButton = closeButton;
        return (B) this;
    }

    @Override
    @NonNull
    public B setLayout(@LayoutRes int layoutRes) {
        this.layoutRes = layoutRes;
        return (B) this;
    }

    @Override
    @NonNull
    public B setPriority(@IntRange(from = 0) int priority) {
        this.priority = priority;
        return (B) this;
    }

    @NonNull
    @Override
    public B addDrawable(int drawableRes) {
        return addDrawable(controller.provideDefaultDrawableViewId(alertType), drawableRes);
    }

    @NonNull
    @Override
    public B addDrawable(@NonNull Drawable drawable) {
        return addDrawable(controller.provideDefaultDrawableViewId(alertType), drawable);
    }

    @NonNull
    @Override
    public B addDrawable(@Nullable Integer viewId, int drawableRes) {
        return addDrawable(viewId, accessor.getDrawable(getContainer().getActivity(), drawableRes));
    }

    @NonNull
    @Override
    public B addDrawable(@Nullable Integer viewId, @NonNull Drawable drawable) {
        drawableMap.put(viewId != null ? viewId : 0, drawable);
        return (B) this;
    }

    @Override
    public void show() {
        build().show();
    }

    /**
     * Returns the used HitogoContainer object for the alert.
     *
     * @return HitogoContainer of the alert.
     * @see HitogoContainer
     * @since 1.0.0
     */
    @NonNull
    protected HitogoContainer getContainer() {
        return containerRef.get();
    }

    /**
     * Returns the used HitogoController object for the alert.
     *
     * @return HitogoController of the alert.
     * @see HitogoController
     * @since 1.0.0
     */
    @NonNull
    protected HitogoController getController() {
        return controller;
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
     * Returns the used HitogoAccessor object for the alert.
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

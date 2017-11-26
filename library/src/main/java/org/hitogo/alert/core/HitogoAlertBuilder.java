package org.hitogo.alert.core;

import android.arch.lifecycle.Lifecycle;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.util.Log;
import android.util.SparseArray;

import org.hitogo.alert.view.HitogoViewBuilder;
import org.hitogo.button.core.HitogoButton;
import org.hitogo.core.HitogoContainer;
import org.hitogo.core.HitogoController;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings({"WeakerAccess", "unused", "unchecked"})
public abstract class HitogoAlertBuilder<T> {

    private Class<? extends HitogoAlert> targetClass;
    private Class<? extends HitogoAlertParams> paramClass;
    private WeakReference<HitogoContainer> containerRef;
    private HitogoController controller;
    private HitogoVisibilityListener visibilityListener;

    private HitogoAlertParamsHolder holder = new HitogoAlertParamsHolder();

    private SparseArray<String> textMap = new SparseArray<>();
    private List<HitogoButton> buttons = new ArrayList<>();
    private HitogoButton closeButton;
    private String title;
    private Integer titleViewId;

    private Bundle privateBundle = new Bundle();
    private Bundle arguments;
    private Integer state;
    private String tag;
    private int hashCode;
    private HitogoAlertType builderType;
    private Integer layoutRes;

    public HitogoAlertBuilder(@NonNull Class<? extends HitogoAlert> targetClass,
                              @NonNull Class<? extends HitogoAlertParams> paramClass,
                              @NonNull HitogoContainer container, @NonNull HitogoAlertType builderType) {
        this.targetClass = targetClass;
        this.paramClass = paramClass;
        this.containerRef = new WeakReference<>(container);
        this.controller = container.getController();
        this.builderType = builderType;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public final HitogoAlert build() {
        if(tag != null) {
            hashCode = tag.hashCode();
        } else {
            tag = textMap.valueAt(0);
            hashCode = tag.hashCode();
        }

        onProvideData(holder);
        onProvidePrivateData(holder);

        try {
            HitogoAlert object = targetClass.getConstructor().newInstance();
            HitogoAlertParams params = paramClass.getConstructor().newInstance();
            params.provideData(holder, privateBundle);
            return object.create(getContainer(), params);
        } catch (Exception e) {
            Log.wtf(HitogoViewBuilder.class.getName(), "Build process failed.");
            throw new IllegalStateException(e);
        }
    }

    private void onProvidePrivateData(HitogoAlertParamsHolder holder) {
        privateBundle.putString("title", title);
        privateBundle.putSerializable("titleViewId", titleViewId);
        privateBundle.putString("tag", tag);
        privateBundle.putInt("hashCode", hashCode);
        privateBundle.putBundle("arguments", arguments);
        privateBundle.putSerializable("type", builderType);
        privateBundle.putSerializable("state", state);
        privateBundle.putSerializable("layoutRes", layoutRes);

        holder.provideVisibilityListener(visibilityListener);
        holder.provideTextMap(textMap);
        holder.provideButtons(buttons);
        holder.provideCloseButton(closeButton);
    }

    protected abstract void onProvideData(HitogoAlertParamsHolder holder);

    @NonNull
    public final T controlledBy(HitogoController controller) {
        this.controller = controller;
        return (T) this;
    }

    @NonNull
    public final T setBundle(@NonNull Bundle arguments) {
        this.arguments = arguments;
        return (T) this;
    }

    @NonNull
    public final T setTitle(@NonNull String title) {
        return setTitle(getController().provideDefaultTitleViewId(builderType), title);
    }

    @NonNull
    public final T setTitle(Integer viewId, @NonNull String title) {
        this.titleViewId = viewId;
        this.title = title;
        return (T) this;
    }

    @NonNull
    public final T addText(@NonNull String text) {
        return addText(getController().provideDefaultTextViewId(builderType), text);
    }

    @NonNull
    public final T addText(Integer viewId, @NonNull String text) {
        textMap.put(viewId, text);
        return (T) this;
    }

    @NonNull
    public final T setTag(@NonNull String tag) {
        this.tag = tag;
        return (T) this;
    }

    @NonNull
    public final T withState(Integer state) {
        this.state = state;
        return (T) this;
    }

    @NonNull
    public final T withState(Enum state) {
        this.state = state != null ? state.ordinal() : null;
        return (T) this;
    }

    @NonNull
    public final T addVisibilityListener(@NonNull HitogoVisibilityListener visibilityListener) {
        this.visibilityListener = visibilityListener;
        return (T) this;
    }

    @NonNull
    public final T addButton(@NonNull HitogoButton... buttons) {
        Collections.addAll(this.buttons, buttons);
        return (T) this;
    }

    @NonNull
    public final T addCloseButton(@NonNull HitogoButton closeButton) {
        this.closeButton = closeButton;
        return (T) this;
    }

    @NonNull
    public final T setLayout(@LayoutRes Integer layoutRes) {
        this.layoutRes = layoutRes;
        return (T) this;
    }

    public final void show() {
        show(false);
    }

    public final void show(boolean force) {
        HitogoContainer container = containerRef.get();
        if(container.getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
            build().show(force);
        } else {
            build().showDelayed(HitogoAlert.DEFAULT_SHOW_DELAY_IN_MS, force);
        }
    }

    public final void showDelayed(long millis) {
        build().showDelayed(millis, false);
    }

    public final void showDelayed(long millis, boolean force) {
        build().showDelayed(millis, force);
    }

    protected final HitogoContainer getContainer() {
        return containerRef.get();
    }

    protected final HitogoController getController() {
        return controller;
    }
}
